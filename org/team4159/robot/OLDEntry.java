package org.team4159.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.ADXL345_I2C.AllAxes;
import edu.wpi.first.wpilibj.ADXL345_I2C.Axes;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.MonoImage;
import edu.wpi.first.wpilibj.image.NIVision;
import org.team4159.robot.parts.AbsoluteTimer;
import org.team4159.robot.parts.AdjustedJoystick;
import org.team4159.robot.parts.AnalogUltrasonic;
import org.team4159.robot.www.RobotServer;
import com.sun.cldc.jna.TaskExecutor;
import com.sun.squawk.Klass;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT;
import edu.wpi.first.wpilibj.image.*;

public class OLDEntry extends RobotBase {
	
	// THESE NEED TUNING
	private final static double MOTOR_PID_KP = 1.0;
	private final static double MOTOR_PID_KI = 1.0;
	private final static double MOTOR_PID_KD = 1.0;
	private final static double MOTOR_SPEED_COEFFICIENT = 4.0;
	
	private Jaguar leftMotor = new Jaguar (HWPorts.Digital_Sidecar.PWM.LEFT_MOTOR_JAGUAR);//
	private Jaguar rightMotor = new Jaguar (HWPorts.Digital_Sidecar.PWM.RIGHT_MOTOR_JAGUAR);//
	// TO DO:  need to switch left/ right encoder ports or names;
	private Encoder leftEncoder = new Encoder(HWPorts.Digital_Sidecar.DigitalIO.LEFT_DRIVE_ENCODER_A_SOURCE,HWPorts.Digital_Sidecar.DigitalIO.LEFT_DRIVE_ENCODER_B_SOURCE);//
	private Encoder rightEncoder = new Encoder(HWPorts.Digital_Sidecar.DigitalIO.RIGHT_DRIVE_ENCODER_A_SOURCE,HWPorts.Digital_Sidecar.DigitalIO.RIGHT_DRIVE_ENCODER_B_SOURCE);//
	
	// UNCOMMENT WHEN PID IS IMPLEMENTED
	/*
	private PIDController leftPIDController = new PIDController (
			MOTOR_PID_KP, MOTOR_PID_KI, MOTOR_PID_KD, leftEncoder, leftMotor);
	private PIDController rightPIDController = new PIDController (
			MOTOR_PID_KP, MOTOR_PID_KI, MOTOR_PID_KD, rightEncoder, rightMotor);
	private RobotDrive drive = new RobotDrive (
			new PIDSpeedBridge (leftPIDController, MOTOR_SPEED_COEFFICIENT),
			new PIDSpeedBridge (rightPIDController, MOTOR_SPEED_COEFFICIENT)
	);
	*/

	private RobotDrive drive = new RobotDrive (leftMotor, rightMotor);//
	
	private AdjustedJoystick driveStick = new AdjustedJoystick (HWPorts.USBController.DRIVE_STICK);//
	private AdjustedJoystick cameraStick = new AdjustedJoystick (HWPorts.USBController.CAMERA_STICK);//
	
	private Servo cameraHorzServo = new Servo (HWPorts.Digital_Sidecar.PWM.CAMERA_HORIZONTAL_SERVO);//
	private Servo cameraVertServo = new Servo (HWPorts.Digital_Sidecar.PWM.CAMERA_VERTICAL_SERVO);//
	
	private DriverStation ds;
	private AxisCamera camera = AxisCamera.getInstance ("10.41.59.11");//
	
	private AnalogUltrasonic UltrasonicSensorFront = new AnalogUltrasonic(HWPorts.AnalogInput.UNUSED_2);//
        
	private AbsoluteTimer autonomousTimer = new AbsoluteTimer (10);
	private AbsoluteTimer operatorTimer = new AbsoluteTimer (1);
	
	private ADXL345_I2C accelerometer = new ADXL345_I2C (SensorBase.getDefaultDigitalModule (), ADXL345_I2C.DataFormat_Range.k16G);//
    private Gyro gyroSensor = new Gyro(2);//
    
	public OLDEntry ()
	{
		getWatchdog ().setEnabled (false);
		leftMotor.enableDeadbandElimination (true);//
		rightMotor.enableDeadbandElimination (true);//
		ds = m_ds;
		
		/* TODO: reverse motor wiring so we don't have to do this. */
		drive.setInvertedMotor (RobotDrive.MotorType.kRearLeft, true);//
		
		// add mappings for sticks
		driveStick.setMapping (Joystick.AxisType.kX, 1.0, 0.04, 0.6, 1.0);//
		driveStick.setMapping (Joystick.AxisType.kY, 1.0, 0.04, 0.5, 1.0);//
		cameraStick.setMapping (null, 1.0, 0.04, 1.0, 1.0);//
		
		leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);//
		leftEncoder.setDistancePerPulse((6*.0254/180)*5/19.1460966666666666666666666);//
		leftEncoder.start();//

		gyroSensor.reset();//
		
		// UNCOMMENT WHEN PID IS IMPLEMENTED
		/*
		leftPIDController.enable ();
		rightPIDController.enable ();
		*/
		
		camera.writeCompression (30);//
		camera.writeResolution (ResolutionT.k320x240);//

		final RobotServer server = new RobotServer ();
		server.start ();
		
		(new Thread () {
			private final Axes[] AXES = {Axes.kX, Axes.kY, Axes.kZ};
			public void run ()
			{
				StringBuffer build = new StringBuffer (25);
				StringBuffer output = new StringBuffer (65);
				for (;;)
				{
					output.setLength (0);
					for (int i = 0; i < AXES.length; i++)
					{
						build.setLength (0);
						build.append (accelerometer.getAcceleration (AXES[i]));
						while (build.length () < 20)
							build.append (' ');
						output.append (build);
					}
					System.out.println (output);
					Timer.delay (0.250);
				}
			}
		}).start ();
		
		(new Thread () {	
			public void run ()
			{
				CriteriaCollection criteria = new CriteriaCollection ();
				
				for (;;)
				{
					if (!camera.freshImage ())
						continue;
					
					ColorImage img = null; // the raw camera input
					ColorImage imgeq = null; // the luminance-equalized raw camera input
					BinaryImage search = null; // part of the image in the good range
					BinaryImage cleaned = null; // no small objects
					BinaryImage hulled = null; // convex-hulled
					
					try {
						img = camera.getImage ();
						imgeq = img.luminanceEqualize ();
						search = imgeq.thresholdHSL (0, 255, 0, 40, 144, 255);
						cleaned = search.removeSmallObjects (true, 2);
						hulled = cleaned.convexHull (true);
						/* HACKXING TIME */
						
						// write out final particle output to file
						search.write ("search.png");
						cleaned.write ("cleaned.png");
						hulled.write ("hulled.png");
					} catch (Throwable e) {
						e.printStackTrace ();
						throw new RuntimeException (e.toString ());
					} finally {
						try {
							if (img != null)
								img.free ();
							if (imgeq != null)
								imgeq.free ();
							if (search != null)
								search.free ();
							if (cleaned != null)
								cleaned.free ();
							if (hulled != null)
								hulled.free ();
						} catch (NIVisionException e) {}
					}

					Timer.delay (0.250);
				}
			}
		}).start ();
	}
	
	public void startCompetition () {
		
		System.out.println ("Entering main code.");
		
		while (true)
		{
			try {
				if (isDisabled())
					runDisabled ();
				else if (isAutonomous())
					runAutonomous ();
				else
					runOperator ();
				getWatchdog ().feed ();
			} catch (Throwable t) {
				System.err.println ("SEVERE ERROR: " + t);
				t.printStackTrace ();
			}
		}
	}
	
	private void runDisabled ()
	{
		drive.stopMotor();//
		Thread.yield ();
	}
	
	private void runAutonomous ()
	{
		autonomousTimer.startDelayedCode ();
		
		ColorImage img;
		MonoImage hue; 
		
		/*
		try {
			img = camera.getImage ();
			hue = img.getHSLHuePlane ();
			
			BinaryImage bi = BinaryImage.class.newInstance ();
		} catch (Exception e) { e.printStackTrace (); }
		*/
		
		/*
		ColorImage img;
		MonoImage mi;
		try {
			img = camera.getImage ();
			mi = img.getIntensityPlane ();
			System.out.println (mi.getWidth () + " " + mi.getHeight ());
			System.out.println (mi.image.getSize ());
		} catch (Exception e) { e.printStackTrace(); }
		*/
		
		/*
		if(leftEncoder.getDistance()<1)
			drive.arcadeDrive(0, -1.0);
		else
			drive.arcadeDrive(0, 0);
		*/
		
		autonomousTimer.endDelayedCode ();
	}

	private void runOperator ()
	{
		operatorTimer.startDelayedCode ();
		
		/* use joystick input */
		drive.arcadeDrive (driveStick.getX (), driveStick.getY ()); //
				
		if(driveStick.getRawButton(3))
			System.out.println(UltrasonicSensorFront);
		if(driveStick.getRawButton(10))
			leftEncoder.reset();
		if(driveStick.getRawButton(5))
			System.out.println("encoder raw readings : " + leftEncoder.getDistance());
		if(driveStick.getRawButton(6))
			System.out.println(gyroSensor.getAngle());
		if(driveStick.getRawButton(7))
			gyroSensor.reset();
                
		cameraHorzServo.set ((cameraStick.getX () + 1) / 2);//
		cameraVertServo.set ((cameraStick.getY () + 1) / 2);//
		
		operatorTimer.endDelayedCode ();
	}
}