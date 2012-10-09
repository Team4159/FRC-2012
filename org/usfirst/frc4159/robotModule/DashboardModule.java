package org.usfirst.frc4159.robotModule;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;

public class DashboardModule extends Module {
	private Dashboard board;
	public DashboardModule(){
		board = DriverStation.getInstance().getDashboardPackerLow();
	}
	
	public void updateDashboard(){
		board.addCluster();
        {
            board.addCluster();
            {     //analog modules
                board.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        board.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                board.finalizeCluster();
            }
            board.finalizeCluster();

            board.addCluster();
            { //digital modules
                board.addCluster();
                {
                    board.addCluster();
                    {
                        int module = 1;
                        board.addByte(DigitalModule.getInstance(module).getRelayForward());
                        board.addByte(DigitalModule.getInstance(module).getRelayForward());
                        board.addShort(DigitalModule.getInstance(module).getAllDIO());
                        board.addShort(DigitalModule.getInstance(module).getDIODirection());
                        board.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                board.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        board.finalizeCluster();
                    }
                    board.finalizeCluster();
                }
                board.finalizeCluster();

            }
            board.finalizeCluster();

            board.addByte(Solenoid.getAllFromDefaultModule());
        }
        board.finalizeCluster();
        board.commit();
	}
	
	private static DashboardModule instance;
	public static synchronized DashboardModule getInstance(){
		if(instance == null)
			instance = new DashboardModule();
		return instance;
	}

}
