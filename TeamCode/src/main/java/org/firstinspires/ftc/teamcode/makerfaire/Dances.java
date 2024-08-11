package org.firstinspires.ftc.teamcode.makerfaire;

//Stores our dance moves

//Dance move 1 - Spinning500
    // 500 milliseconds of fast pace spinning

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.haaslibs.Hardware;

public class Dances {
    public boolean spinning(Hardware hardware, double startingTime, double timeNow, double seconds)
    {
        if (timeNow - seconds >= startingTime) {
          //  hardware.reset();
            return false;
        }
        //hardware.frontLeft.setPower(.75);;
        //hardware.backLeft.setPower(.75);;
        //hardware.backRight.setPower(-.75);
        //hardware.frontRight.setPower(-75);
        return true;
    }

    public boolean driving(Hardware hardware, double power, double startingTime, double timeNow, double seconds)
    {
        if (timeNow - seconds >= startingTime) {
          //  hardware.reset();
            return false;
        }
        //hardware.frontLeft.setPower(power);;
        //hardware.backLeft.setPower(power);;
        //hardware.backRight.setPower(power);
        //hardware.frontRight.setPower(power);
        return true;
    }




}
