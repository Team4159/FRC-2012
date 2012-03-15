
package org.team4159.robot;

/**
 *
 * @author Waylin
 */

public interface HWPorts {
    
    // connected to computer
    interface USBController
    {
        int DRIVE_STICK          =  1; //standard joystick
        int CAMERA_STICK         =  2; // standard joystick
        int UNUSED_3             =  3;
        int UNUSED_4             =  4;
    }
    
    //on NI-92**  need model number.
    interface AnalogInput
    {
        int ULTRASONIC_BACK      =  1;//ultrasonic sensor for front
        int UNUSED_2             =  2;
        int UNUSED_3             =  3;
        int UNUSED_4             =  4;
        int UNUSED_5             =  5;
        int UNUSED_6             =  6;
        int UNUSED_7             =  7;
        int BATTERY_VOLTAGE      =  8;
    }
    
    //digitall sidecar ports including PWM, Relay, DigitalIO, I2CSpare
    interface Digital_Sidecar
    {
        //10 PWM ports in total
        interface PWM
        {
            int LEFT_MOTOR_JAGUAR          =  1; //jaguar control for left side drive motors
            int RIGHT_MOTOR_JAGUAR         =  2; //jaguar control for right side drive motors
            int CAMERA_HORIZONTAL_SERVO    =  3; // horizontal servo control for camera, rotates left/right
            int CAMERA_VERTICAL_SERVO      =  4; // vertical servo control for camera, rotates up/down
            int BRIDGE_MANIP_VICTOR        =  5;// single victor for two motors on bridge manipulator
            int UNUSED_6                   =  6;
            int BALL_LAUNCHER_UPPER_MOTOR  =  7;
            int BALL_LAUNCHER_LOWER_MOTOR  =  8;
            int UNUSED_9                   =  9;
            int UNUSED_10                  =  10;
        }
        
        //8 Relay Ports
        interface Relay
        {
            int UNUSED_1              =  1;
            int UNUSED_2              =  2;
            int UPPER_PICKUP          =  3;
            int LOWER_PICKUP          =  4;
            int UNUSED_5              =  5;
            int UNUSED_6              =  6;
            int UNUSED_7              =  7;
            int UNUSED_8              =  8;
        }
        
        //14 Digital I/O Ports
        interface DigitalIO
        {
            int LEFT_DRIVE_ENCODER_A_SOURCE          =  1;
            int LEFT_DRIVE_ENCODER_B_SOURCE          =  2;
            int UNUSED_3                             =  3;
            int UNUSED_4                             =  4;
            int UNUSED_5                             =  5;
            int UNUSED_6                             =  6;
            int UNUSED_7                             =  7;
            int UNUSED_8                             =  8;
            int UNUSED_9                             =  9;
            int UNUSED_10                            =  10;
            int RIGHT_DRIVE_ENCODER_A_SOURCE         =  11;
            int RIGHT_DRIVE_ENCODER_B_SOURCE         =  12;
            int UNUSED_13                            =  13;
            int UNUSED_14                            =  14;
        }
    }
    
}
