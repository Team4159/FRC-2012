package org.team4159.robot.modules;

public abstract class Module
{
	protected boolean essential = false;
	public void enterDisabled () {}
	public void runDisabled () {}
	public void enterAutonomous () {}
	public void runAutonomous () {}
	public void enterOperator () {}
	public void runOperator () {}
}
