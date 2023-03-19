package frc.robot;

/**
 * The Constants class is where all tunable settings for the robot live.
 * The purpose of putting them here is so that you don't have to search through code to adjust settings.
 */
public class Constants {

    public class Global {
        public static final boolean hasCamera = true; //set to false if no USB camera on Robo Rio
        public static final boolean debugMode = true; //set to true to show debugging messages in log
    }

    /** 
     * The IDs class is where the IDs (CAN IDs, PWM ports, relays, controllers, etc.) are setup.
     */
    public class IDs {
        /**
         * This class configures the IDs of the drivetrain system
         */
        public class DriveTrain {
            //IDs of drivetrain controllers
            public static final int driveLeftFront = 5;
            public static final int driveLeftRear = 6;
            public static final int driveRightFront = 3;
            public static final int driveRightRear = 4;
        }

        /**
         * This class configures the IDs of the Solenoids on the pneumatic system
         */
        public class Solenoid {
            public static final int driveHighGear = 0; //shift to high gear (ball shifter)
            public static final int driveLowGear = 1; //shift to low gear (ball shifter)
            public static final int intakeDeploy = 2; //deploy intake system (default retracted)
        }

        /**
         * This class configures the IDs of the controllers for an arm using 2 motor controllers
         */
        public class Arm {
            public static final int armLeft = 7;
            public static final int armRight = 8;
        }

        /**
         * This class configures the IDs of the Joysticks and buttons for doing things
         * Rather than setting the IDs directly, use the Controllers class to abstract the IDs to button names
         */
        public class OI {
            //setup the controller ids first
            public static final int driverJoy = 0; //The joystick ID of the drivers joystick (in DriverStation USB section)
            public static final int operJoy = 1; //The joystick ID of the operators joystick (in DriverStation USB section)

            //assign axis
            public static final int axis_throttle = 0;
            public static final int axis_turn = 4;

            //assign buttons
            public static final int btn_IntakeDeploy = Controllers.Logitech.BTN_LB; 
            public static final int btn_shiftHigh = 17; //fake button for example
            public static final int btn_shiftLow = 18; //fake button for example
        }
    }

    //Drivetrain
    public class DriveTrain {
        public static final double kSpeedMultiplier = 0.75; //If the robot is too fast, reduce the speed here (0.0-1.0)
        public static final double kTurnMutliplier = 0.75; //If the robot turns too fast, reduce the rate of turn here (0.0-1.0)
        public class Left {
            public static final boolean isInverted = true; //invert the left motors
        }
        public class Right {
            public static final boolean isInverted = false; //invert the right motors
        }
    }

    //Intake
    public class Intake {
        public static final boolean kAirStateDeployed = true; //state of the solenoid when intake deployed
    }

    //Auton
    public class Auton {
        public static final double kAutonDriveSpeed = 0.6; //drive speed during auton percentage
    }

    //Operator Interface
    public class OI {
        public static final double kMaxDeadband = 0.90; //When the sticks are over this, consider it 100%
        public static final double kMinDeadband = 0.20; //When the sticks are under this, consider it 0%
    }
    
    /**
     * The controllers classes is where button definitions are stored for different types of controllers.
     * Generally, you do not need to edit these. A new subclass may be created for any new type of controller you wish to use.
     */
    public static final class Controllers {
        /**
         * This class defines the hardware button and axis IDs for a Logitech F310 Controller.
         * The buttons array is 1-based, but the axis array is 0-based
         */
        public static final class Logitech {
            static final int BTN_A = 1; //A Button
            static final int BTN_B = 2; //B Button
            static final int BTN_X = 3; //X Button
            static final int BTN_Y = 4; //Y Button
            static final int BTN_LB = 5; //Left Bumper (L1)
            static final int BTN_RB = 6; //Right Bumper (R1)
            static final int BTN_BACK = 7; //Back Button (Select)
            static final int BTN_START = 8; //Start Button
            static final int BTN_L = 9; //Left Stick Press (L3)
            static final int BTN_R = 10; //Right Stick Press (R3)
            static final int AXIS_LH = 0; //Left Analog Stick horizontal
            static final int AXIS_LV = 1; //Left Analog Stick vertical
            static final int AXIS_LT = 2; //Analog Left Trigger
            static final int AXIS_RT = 3; //Analog Right Trigger
            static final int AXIS_RH = 4; //Right Analog Stick horizontal
            static final int AXIS_RV = 5; //Right Analog Stick vertical
            static final int DPAD_UP = 0;
            static final int DPAD_UPRIGHT = 45;
            static final int DPAD_RIGHT = 90;
            static final int DPAD_DNRIGHT = 135;
            static final int DPAD_DN = 180;
            static final int DPAD_DNLEFT = 225;
            static final int DPAD_LEFT = 270;
            static final int DPAD_UPLEFT = 315;
            static final int DPAD_IDLE = -1; 
        }

        /**
         * This class defines the hardware button and axis IDs for a Stadia Controller.
         * The buttons array is 1-based, but the axis array is 0-based
         */
        public static final class Stadia {
            //DO NOT EDIT THESE
            static final int BTN_A = 1; //A Button
            static final int BTN_B = 2; //B Button
            static final int BTN_X = 3; //X Button
            static final int BTN_Y = 4; //Y Button
            static final int BTN_LB = 5; //Left Bumper (L1)
            static final int BTN_RB = 6; //Right Bumper (R1)
            static final int BTN_L = 7; //Left Stick Press (L3)
            static final int BTN_R = 8; //Right Stick Press (R3)
            static final int BTN_ELLIPSES = 9; //Ellipsis Button (...)
            static final int BTN_HAMBURGER = 10; //Hamburger Button
            static final int BTN_STADIA = 11; //Stadia Button
            static final int BTN_RT = 12; //Right Trigger (R2)
            static final int BTN_LT = 13; //Left Trigger (L2)
            static final int BTN_GOOGLE = 14; //Google Button
            static final int BTN_FRAME = 15; //Square Frame Button
            static final int AXIS_LH = 0; //Left Analog Stick horizontal (right +)
            static final int AXIS_LV = 1; //Left Analog Stick vertical (down +)
            static final int AXIS_LV2 = 2; //Left Analog Stick vertical (down +) - duplicate
            static final int AXIS_RH = 3; //Right Analog Stick horizontal (right +)
            static final int AXIS_RV = 4; //Right Analog Stick vertical (down +)
            static final int AXIS_RV2 = 5; //Right Analog Stick vertical (down +) - duplicate
            static final int DPAD_UP = 0;
            static final int DPAD_UPRIGHT = 45;
            static final int DPAD_RIGHT = 90;
            static final int DPAD_DNRIGHT = 135;
            static final int DPAD_DN = 180;
            static final int DPAD_DNLEFT = 225;
            static final int DPAD_LEFT = 270;
            static final int DPAD_UPLEFT = 315;
            static final int DPAD_IDLE = -1; 
        }

        /**
         * This class defines the hardware button and axis IDs for a Ultimarc Ultrastik 360
         * The buttons array is 1-based, but the axis array is 0-based
         */
        public static final class Ultrastik {
            static final int BTN_1 = 1;
            static final int BTN_2 = 2;
            static final int BTN_3 = 3;
            static final int BTN_4 = 4;
            static final int BTN_5 = 5;
            static final int BTN_6 = 6;
            static final int BTN_7 = 7;
            static final int BTN_8 = 8;
            static final int BTN_9 = 9;
            static final int BTN_10 = 10;
            static final int BTN_11 = 11;
            static final int BTN_12 = 12;
            static final int BTN_13 = 13;
            static final int BTN_14 = 14;
            static final int BTN_15 = 15;
        }
    }
}
