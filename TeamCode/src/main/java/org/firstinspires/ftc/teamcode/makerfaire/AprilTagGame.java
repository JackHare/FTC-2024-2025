package org.firstinspires.ftc.teamcode.makerfaire;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.haaslibs.AprilTag;
import org.firstinspires.ftc.teamcode.haaslibs.Hardware;
import org.firstinspires.ftc.teamcode.haaslibs.Tensorflow;

import java.util.ArrayList;

@TeleOp
public class AprilTagGame extends OpMode {

    Hardware hardware;

    Tensorflow tensorflow = new Tensorflow();
    AprilTag aprilTag = new AprilTag();
    AprilTag lastAprilTag = new AprilTag();

    ElapsedTime runTime = new ElapsedTime();

    Dances dances = new Dances();

    boolean currentlyDancing = false;
    double timeWhenStartingDancing = 0;

    boolean pollingForAprilTags = true;
    int counter = 0;

    ArrayList <AprilTag> aprilTagIds = new ArrayList<>();

    @Override
    public void init()
    {
        hardware = new Hardware(hardwareMap);
        //init machine learning stuff
        tensorflow.init(hardwareMap);
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
        if (aprilTag.number != 0 && pollingForAprilTags && aprilTag.number != lastAprilTag.number)aprilTagIds.add(aprilTag);
        lastAprilTag = aprilTag;
        telemetry.addData("Number", aprilTag.number);
        telemetry.addData("Name", aprilTag.name);
        telemetry.addData("time", (Math.sin(runTime.time())));

        for(int i = 0; i < aprilTagIds.size();i++)telemetry.addData("id "+ i, aprilTagIds.get(i).number);


        if (gamepad1.a)
        {
            timeWhenStartingDancing = runTime.time();
            pollingForAprilTags = false;
        }
        if (!pollingForAprilTags) {
            if(aprilTagIds.get(counter).number == 583)
            {
                currentlyDancing = dances.spinning(hardware, timeWhenStartingDancing, runTime.time(), 3);
            }
            if(aprilTagIds.get(counter).number == 584)
            {
                currentlyDancing = dances.driving(hardware,.5, timeWhenStartingDancing, runTime.time(), 1);
            }
        }

        if(!currentlyDancing && !pollingForAprilTags)
        {
            currentlyDancing = true;
            counter++;
            if(counter == aprilTagIds.size())requestOpModeStop();
        }

        if (Math.sin(runTime.time())*10 >= .5) {
     //       hardware.arm.setTargetPosition(-1000);
       //     hardware.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //   hardware.arm.setPower(1);
        }else
        {
            //hardware.arm.setTargetPosition(-200);
           // hardware.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           // hardware.arm.setPower(1);
        }
    }

}
