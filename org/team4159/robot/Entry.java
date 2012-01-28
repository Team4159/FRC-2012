package org.team4159.robot;

import org.team4159.boths.Server;
import org.team4159.robot.web.RobotServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.camera.AxisCamera;

public class Entry extends RobotBase {

    private Jaguar leftMotor = new Jaguar(1);
    private Jaguar rightMotor = new Jaguar(2);
    
    private RobotDrive drive = new RobotDrive(leftMotor, rightMotor);
    
    private Joystick driveStick = new Joystick(1);
    private Joystick cameraStick = new Joystick(2);
    
    private Servo cameraHorzServo = new Servo(3);
    private Servo cameraVertServo = new Servo(4);
    
    private DriverStation ds;
    private AxisCamera camera = AxisCamera.getInstance("10.41.59.11");
    
    private AbsoluteTimer autonomousTimer = new AbsoluteTimer(10);
    private AbsoluteTimer operatorTimer = new AbsoluteTimer(5);
    
    private double stickXOffset = 0;
    private double stickYOffset = 0;
    private double stickZOffset = 0;

    public Entry() {
        this.getWatchdog().setEnabled(false);
        //leftMotor.enableDeadbandElimination(true);
        //rightMotor.enableDeadbandElimination(true);
        ds = m_ds;

        /*
         * TODO: reverse motor wiring so we don't have to do this.
         */
        new RobotServer().start();
    }

    public void startCompetition() {

        System.out.println("Entering main code.");

        while (true) {
            try {
                if (isDisabled()) {
                    runDisabled();
                } else if (isAutonomous()) {
                    runAutonomous();
                } else {
                    runOperator();
                }
            } catch (Throwable t) {
                System.err.println("SEVERE ERROR: " + t);
                t.printStackTrace();
            }
        }
    }

    private void runDisabled() {
        /*
         * not much to do here :/
         */
    }

    private void runAutonomous() {
        autonomousTimer.startDelayedCode();

        /*
         * ColorImage img; MonoImage mi; try { img = camera.getImage (); mi =
         * img.getIntensityPlane (); System.out.println (mi.getWidth () + " " +
         * mi.getHeight ()); System.out.println (mi.image.getSize ()); } catch
         * (Exception e) { e.printStackTrace(); }
         */

        autonomousTimer.endDelayedCode();
    }

    private void runOperator() {
        operatorTimer.startDelayedCode();

        /*
         * use joystick input
         */
        //drive.arcadeDrive (driveStick);
        //drive.arcadeDrive (driveStick.getX (), driveStick.getY ());
        cameraHorzServo.set((cameraStick.getX() + 1) / 2);
        cameraVertServo.set((cameraStick.getY() + 1) / 2);
        speedLimit();
        driveRobot();
        
        if (driveStick.getRawButton(3)) {
            joystickCalibration();
            System.out.println("calibrated");
        }
        operatorTimer.endDelayedCode();
    }

    private void speedLimit()
    {
        if(driveStick.getTrigger())
        {
            drive.setMaxOutput(1);
        }
        else
            drive.setMaxOutput(.75);
    }
    private void joystickCalibration() {
        stickXOffset = 0 - driveStick.getX();
        stickYOffset = 0 - driveStick.getY();
        stickZOffset = 0 - driveStick.getZ();
    }

    private void driveRobot() {
        if(stickXOffset == 0 && stickYOffset == 0)
        {
            drive.arcadeDrive((driveStick.getY()), (driveStick.getX()));
            System.out.println("drive style 0");
        }
        if (stickXOffset > 0 && stickYOffset > 0) {
            drive.arcadeDrive((driveStick.getY() + stickYOffset), (driveStick.getX() + stickXOffset));
            System.out.println("drive style 1");
        }
        if (stickXOffset > 0 && stickYOffset < 0) {
            drive.arcadeDrive((driveStick.getY() + stickYOffset), (driveStick.getX() - stickXOffset));
            System.out.println("drive style 2");
        }
        if (stickXOffset < 0 && stickYOffset > 0) {
            drive.arcadeDrive((driveStick.getY() - stickYOffset), (driveStick.getX() + stickXOffset));
            System.out.println("drive style 3");
        }
        if (stickXOffset < 0 && stickYOffset < 0) {
            drive.arcadeDrive((driveStick.getY() - stickYOffset), (driveStick.getX() - stickXOffset));
            System.out.println("drive style 4");
        }
    }
}
