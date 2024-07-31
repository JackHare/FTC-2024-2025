package org.firstinspires.ftc.teamcode.makerfaire;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.haaslibs.AprilTag;
import org.firstinspires.ftc.teamcode.haaslibs.Tensorflow;

@TeleOp
public class AprilTagGame extends OpMode {

    DcMotor frontLeft, backLeft, frontRight, backRight, arm;
    Tensorflow tensorflow = new Tensorflow();
    AprilTag aprilTag = new AprilTag();
    @Override
    public void init()
    {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        arm = hardwareMap.get(DcMotor.class, "arm1");
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        tensorflow.init(hardwareMap);
    }

    @Override
    public void loop()
    {
        arm.setPower(1.0);
        //Poll for april tags
        aprilTag = tensorflow.pollAprilTag();
        telemetry.addData("Number", aprilTag.number);
        telemetry.addData("Name", aprilTag.name);

        if (aprilTag.number == 583)
        {
            telemetry.addData("583", "583");
         //   frontLeft.setPower(0.5);
            arm.setTargetPosition(-100);
        }
        if (aprilTag.number == 584)
        {
            telemetry.addData("584", "584");
         //   frontLeft.setPower(-0.5);
            arm.setTargetPosition(-200);
        }else {
            telemetry.addData("585", "585");
            //frontLeft.setPower(0);
            arm.setTargetPosition(0);

        }

    }

}
