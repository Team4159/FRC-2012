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
        return super.getVoltage();
    }
    public double getDistanceInInches()
    {
        return (super.getVoltage()*1000) / 9.666666666666666666666666666666;
    }
    
    public String toString()
    {
        return "the distance is " + (int)this.getDistanceInInches() + " inches.";
    }
    
}