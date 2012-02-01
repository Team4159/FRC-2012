package org.team4159.robot;

import org.team4159.boths.Server;
import org.team4159.robot.www.RobotServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.Encoder;

public class Entry extends RobotBase {
	
	private Jaguar leftMotor = new Jaguar (1);
	private Jaguar rightMotor = new Jaguar (2);
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
	
        private Encoder leftEncoder = new Encoder(1,2);
	
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
		leftEncoder.start();
                leftEncoder.setDistancePerPulse((6*.0254/180)*5/19.1460966666666666666666666);
		//new RobotServer ().start ();
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
