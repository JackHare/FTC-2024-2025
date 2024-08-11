package org.firstinspires.ftc.teamcode.makerfaire;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.haaslibs.AprilTag;
import org.firstinspires.ftc.teamcode.haaslibs.Hardware;
import org.firstinspires.ftc.teamcode.haaslibs.Movement;
import org.firstinspires.ftc.teamcode.haaslibs.Tensorflow;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

@TeleOp
public class Polling extends LinearOpMode {

    //CV stuff
    Tensorflow tensorflow = new Tensorflow();
    AprilTag aprilTag = new AprilTag();
    ArrayList<AprilTag> aprilTags = new ArrayList<>();
    ArrayList<AprilTag> aprilTagResults = new ArrayList<>();

    Set<Integer> AprilTagIds = new HashSet<>();

    Hardware hardware;

    @Override
    public void runOpMode() {
        //Init
        tensorflow.init(hardwareMap);

        hardware = new Hardware(hardwareMap);

        //Start
        waitForStart();

        //Loop
        while (opModeIsActive()) {
            aprilTagResults = tensorflow.pollAprilTag();
            telemetry.addData("size", aprilTagResults.size());

            for (int i = 0; i < aprilTagResults.size(); i++) {

                if (!AprilTagIds.contains(aprilTagResults.get(i).number)) {
                    aprilTags.add(aprilTagResults.get(i));
                    AprilTagIds.add(aprilTagResults.get(i).number);
                }

            }

            for(int i = 0; i < aprilTags.size();i++)
            {
                telemetry.addData("number", aprilTags.get(i).number);
            }

            Movement movement = new Movement(1.0, -1000, -1000);
            hardware.move(movement);


            telemetry.update();
        }

    }


}
