package org.usfirst.frc4159;

public interface HWPorts {

	//DriverStation line number
	interface DSLineNum{
		int SPEED					= 0;
		int BALL_LAUNCH_PROGRESS	= 1;
		int LAUNCHER_POWER			= 2;
		int MILEAGE					= 3;
		int UNUSED_LINE_4			= 4;
		int ROBOT_STATUS			= 5;
	}
	//connected to computer
	interface USBController{
		int DRIVE_STICK             = 1;//Logitech joystick
		int CAMERA_STICK            = 2;//Logitech joystick
		int KINET					= 3;//Kinet Controller
		int UNUSED_4				= 4;
	}
	
	//on NI-92
	interface AnalogInput{
		int ULTRASONIC				= 1;
		int UNUSED_2				= 2;
		int UNUSED_3				= 3;
		int UNUSED_4				= 4;
		int UNUSED_5				= 5;
		int UNUSED_6				= 6;
		int UNUSED_7				= 7;
		int BATTERY_VOLTAGE			= 8;
		
	}
	
	//digital sidecar ports
	interface DigitalSideCar{
	
		interface PWM{
			int LEFT_MOTOR_JAGUAR	= 1;
			int RIGHT_MOTOR_JAGUAR	= 2;
			int CAMERA_HORIZ_SERVO	= 3;
			int CAMERA_VERTI_SERVO	= 4;
			int BRIDGE_MANIP_VICTOR	= 5;
			int UNUSED_6			= 6;
			int BALL_LAUNCH_UPPER	= 7;
			int BALL_LAUNCH_LOWER	= 8;
			int BRIDGE_LOCK_SERVO	= 9;
			int UNUSED_10			= 10;
		}
		
		interface Relay{
			int UNUSED_1			= 1;
			int UNUSED_2			= 2;
			int LOWER_PICKUP		= 3;
			int UPPER_PICKUP		= 4;
			int UNUSED_5			= 5;
			int UNUSED_6			= 6;
			int UNUSED_7			= 7;
			int UNUSED_8			= 8;
		}
		
		interface DigitalIO{
			int LEFT_ENCODER_A		= 1;
			int LEFT_ENCODER_B		= 2;
			int LOWER_LAUNCHER_ENCODER_A			= 3;
			int LOWER_LAUNCHER_ENCODER_B			= 4;
			int UNUSED_5			= 5;
			int UNUSED_6			= 6;
			int UNUSED_7			= 7;
			int UNUSED_8			= 8;
			int UNUSED_9			= 9;
			int UNUSED_10			= 10;
			int RIGHT_ENCODER_A		= 11;
			int RIGHT_ENCODER_B		= 12;
			int UPPER_LAUNCHER_ENCODER_A			= 13;
			int UPPER_LAUNCHER_ENCODER_B			= 14;
		}
	}
}
