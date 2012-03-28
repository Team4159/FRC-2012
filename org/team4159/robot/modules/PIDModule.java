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
	// MAKE SURE THESE ARE POSITIVE OR YOU DIE!!!
	// IF YOU THINK I'M JOKING... DO IT AT YOUR OWN RISK.
	private static final double PID_KP = 0.300;
	private static final double PID_KI = 0.140;
	private static final double PID_KD = 0.006;
	
	private static final double SPEED_COEFFICIENT = 6.0;
	
	private final PIDController leftPIDController;
	private final PIDController rightPIDController;
	private final PIDSetpointController leftPIDBridge;
	private final PIDSetpointController rightPIDBridge;
	
	private boolean pidEnabled = false;
	
	private PIDModule ()
	{
		essential = true;
		
		EncoderModule em = EncoderModule.getInstance ();
		MotorModule mm = MotorModule.getInstance ();
		
		leftPIDController = new PIDController (PID_KP, PID_KI, PID_KD,
			em.getLeftEncoder (), mm.getLeftMotor ());
		rightPIDController = new PIDController (PID_KP, PID_KI, PID_KD,
			em.getRightEncoder (), mm.getRightMotor ());
		
		leftPIDBridge = new PIDSetpointController (leftPIDController, mm.getLeftMotor (), SPEED_COEFFICIENT, true);
		rightPIDBridge = new PIDSetpointController (rightPIDController, mm.getRightMotor (), SPEED_COEFFICIENT, false);
		
		//enablePID ();
	}
	
	public void enterAutonomous ()
	{
		disablePID ();
		MotorModule.getInstance().getLeftMotor().set (0.0);
		MotorModule.getInstance().getRightMotor().set (0.0);
	}

	public void enterOperator ()
	{
		enablePID ();
		leftPIDController.reset ();
		rightPIDController.reset ();
	}
	
	public void runOperator ()
	{
		if (DriveStickModule.getInstance ().isDisablePIDPressed ())
			disablePID ();
		else
			enablePID ();
	}
	
	private void enablePID ()
	{
		if (pidEnabled)
			return;
		pidEnabled = true;
		
		leftPIDController.enable ();
		rightPIDController.enable ();
		resetPID ();
	}
	
	private void disablePID ()
	{
		if (!pidEnabled)
			return;
		pidEnabled = false;
		
		leftPIDController.disable ();
		rightPIDController.disable ();
		resetPID ();
	}
	
	private void resetPID ()
	{
		leftPIDController.reset ();
		rightPIDController.reset ();
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