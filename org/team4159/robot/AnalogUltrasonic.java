package org.team4159.robot;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Waylin
 */
public class AnalogUltrasonic extends AnalogChannel
{
    public AnalogUltrasonic(int channel)
    {
        super(channel);
    }
    public double getVoltage()
    {
        return this.getVoltage();
    }
    public double getDistanceInInches()
    {
        return (this.getVoltage()*1000) / 9.7666;
    }
    
    public String toString()
    {
        return "the distance is " + this.getDistanceInInches() + " inches.";
    }
    
}