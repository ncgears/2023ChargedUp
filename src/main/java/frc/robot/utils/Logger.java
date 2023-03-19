package frc.robot.utils;

import frc.robot.Constants;

public class Logger {
    public static void write(String msg) {
        System.out.println(msg);
    }

    public static void debug(String msg) {
        if(Constants.Global.debugMode) System.out.println(msg);
    }
}
