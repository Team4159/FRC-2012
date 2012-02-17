package org.team4159.robot.modules;

import org.team4159.robot.parts.PIDSetpointController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;

public class PIDModule extends Module
{
	/*														*
					     _.--""--._
					    /  _    _  \
					 _  ( (_\  /_) )  _
					{ \._\   /\   /_./ }
					/_"=-.}______{.-="_\
					 _  _.=("""")=._  _
					(_'"_.-"`~~`"-._"'_)
					 {_"            "_}
	 *														*/
	// MAKE SURE THESE ARE NEGATIVE OR YOU DIE!!!
	// IF YOU THINK I'M JOKING... DO IT AT YOUR OWN RISK.
	private static final double PID_KP = -0.24;
	private static final double PID_KI = -0.04;
	private static final double PID_KD = -0.04;
	
	private static final double SPEED_COEFFICIENT = 4.8;
	
	private final PIDController leftPIDController;
	private final PIDController rightPIDController;
	private final PIDSetpointController leftPIDBridge;
	private final PIDSetpointController rightPIDBridge;
	
	private PIDModule ()
	{
		essential = true;
		
		EncoderModule em = EncoderModule.getInstance ();
		MotorModule mm = MotorModule.getInstance ();
		
		leftPIDController = new PIDController (PID_KP, PID_KI, PID_KD,
			em.getLeftEncoder (), mm.getLeftMotor ());
		rightPIDController = new PIDController (PID_KP, PID_KI, PID_KD,
			em.getRightEncoder (), mm.getRightMotor ());
		
		leftPIDBridge = new PIDSetpointController (leftPIDController, SPEED_COEFFICIENT, true);
		rightPIDBridge = new PIDSetpointController (rightPIDController, SPEED_COEFFICIENT, false);
		
		leftPIDController.enable ();
		rightPIDController.enable ();
	}
	
	public RobotDrive createDrive ()
	{
		return new RobotDrive (leftPIDBridge, rightPIDBridge);
	}
	
	private static PIDModule instance;
	public static synchronized PIDModule getInstance ()
	{
		if (instance == null)
			instance = new PIDModule ();
		return instance;
	}
}
