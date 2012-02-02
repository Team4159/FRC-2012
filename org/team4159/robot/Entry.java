package org.team4159.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import org.team4159.robot.www.RobotServer;

public class Entry extends RobotBase {
	
	// THESE NEED TUNING
	private final static double MOTOR_PID_KP = 1.0;
	private final static double MOTOR_PID_KI = 1.0;
	private final static double MOTOR_PID_KD = 1.0;
	private final static double MOTOR_SPEED_COEFFICIENT = 4.0;
	
	private Jaguar leftMotor = new Jaguar (1);
	private Jaguar rightMotor = new Jaguar (2);
	
	private Encoder leftEncoder = new Encoder(1,2);
	private Encoder rightEncoder = new Encoder(3,4);
	
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

	private RobotDrive drive = new RobotDrive (leftMotor, rightMotor);
	
	private AdjustedJoystick driveStick = new AdjustedJoystick (1);
	private AdjustedJoystick cameraStick = new AdjustedJoystick (2);
	
	private Servo cameraHorzServo = new Servo (3);
	private Servo cameraVertServo = new Servo (4);
	
	private DriverStation ds;
	private AxisCamera camera = AxisCamera.getInstance ("10.41.59.11");
	
	private AnalogUltrasonic UltrasonicSensorFront = new AnalogUltrasonic(1);
        
	private AbsoluteTimer autonomousTimer = new AbsoluteTimer (10);
	private AbsoluteTimer operatorTimer = new AbsoluteTimer (5);
	
	public Entry ()
	{
		this.getWatchdog ().setEnabled (false);
		leftMotor.enableDeadbandElimination (true);
		rightMotor.enableDeadbandElimination (true);
		ds = m_ds;
		
		/* TODO: reverse motor wiring so we don't have to do this. */
		drive.setInvertedMotor (RobotDrive.MotorType.kRearLeft, true);
		
		// add mappings for sticks
		driveStick.setMapping (Joystick.AxisType.kX, 1.0, 0.04, 0.6, 1.0);
		driveStick.setMapping (Joystick.AxisType.kY, 1.0, 0.04, 0.5, 1.0);
		cameraStick.setMapping (null, 1.0, 0.04, 1.0, 1.0);
		
		leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		leftEncoder.setDistancePerPulse((6*.0254/180)*5/19.1460966666666666666666666);
		leftEncoder.start();
		
		// UNCOMMENT WHEN PID IS IMPLEMENTED
		/*
		leftPIDController.enable ();
		rightPIDController.enable ();
		*/

		final RobotServer server = new RobotServer ();
		server.start ();
		
		(new Thread () {
			public void run () {
				int i = 0;
				for (;;)
				{
					server.broadcast.sendMessage (Integer.toString (i));
					Timer.delay (1.0);
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
			} catch (Throwable t) {
				System.err.println ("SEVERE ERROR: " + t);
				t.printStackTrace ();
			}
		}
	}
	
	private void runDisabled ()
	{
		drive.stopMotor();
	}
	
	private void runAutonomous ()
	{
		autonomousTimer.startDelayedCode ();
		
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
		
		if(leftEncoder.getDistance()<1)
			drive.arcadeDrive(0, -1.0);
		else
			drive.arcadeDrive(0, 0);
		
		autonomousTimer.endDelayedCode ();
	}

	private void runOperator ()
	{
		operatorTimer.startDelayedCode ();
		
		/* use joystick input */
		drive.arcadeDrive (driveStick.getX (), driveStick.getY ());
				
		if(driveStick.getRawButton(3))
			System.out.println(UltrasonicSensorFront);
		if(driveStick.getRawButton(10))
			leftEncoder.reset();

		System.out.println("encoder raw readings : " + leftEncoder.getDistance());
                
		cameraHorzServo.set ((cameraStick.getX () + 1) / 2);
		cameraVertServo.set ((cameraStick.getY () + 1) / 2);
		
		operatorTimer.endDelayedCode ();
	}
}