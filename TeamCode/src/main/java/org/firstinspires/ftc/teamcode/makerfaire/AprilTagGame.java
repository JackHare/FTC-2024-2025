package org.firstinspires.ftc.teamcode.makerfaire;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.haaslibs.AprilTag;
import org.firstinspires.ftc.teamcode.haaslibs.Hardware;
import org.firstinspires.ftc.teamcode.haaslibs.Tensorflow;

@TeleOp
public class AprilTagGame extends OpMode {

    Hardware hardware;

    Tensorflow tensorflow = new Tensorflow();
    AprilTag aprilTag = new AprilTag();

    ElapsedTime runTime = new ElapsedTime();

    Dances dances = new Dances();

    boolean currentlyDancing = false;
    double timeWhenStartingDancing = 0;

    int temp = 0;
    @Override
    public void init()
    {
        hardware = new Hardware(hardwareMap);
        //init machine learning stuff
        tensorflow.init(hardwareMap);
        currentlyDancing = true;
        timeWhenStartingDancing = runTime.time();
        telemetry.speak("Hello, hello hello hello");
    }

    @Override
    public void start()
    {
        runTime.reset();
    }

    @Override
    public void loop() {
        //Poll for april tags
        aprilTag = tensorflow.pollAprilTag();
        telemetry.addData("Number", aprilTag.number);
        telemetry.addData("Name", aprilTag.name);

        if (currentlyDancing) {
            if (temp == 0)
            currentlyDancing = dances.spinning(hardware, timeWhenStartingDancing, runTime.time(), 5);
            if (temp == 1)
                currentlyDancing = dances.driving(hardware, .5,timeWhenStartingDancing, runTime.time(), .5);
            else {
                currentlyDancing = dances.driving(hardware, -.5,timeWhenStartingDancing, runTime.time(), .5);
                temp = 0;
            }
        } else
       {
           temp += 1;
           currentlyDancing = true;
           timeWhenStartingDancing = runTime.time();
       }

        telemetry.addData("time", (Math.sin(runTime.time())));

        if (Math.sin(runTime.time())*10 >= .5) {
            hardware.arm.setTargetPosition(-1000);
            hardware.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.arm.setPower(1);
        }else
        {
            hardware.arm.setTargetPosition(-200);
            hardware.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.arm.setPower(1);
        }
    }

}
