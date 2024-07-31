package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.haaslibs.AprilTag;
import org.firstinspires.ftc.teamcode.haaslibs.Gamepiece;
import org.firstinspires.ftc.teamcode.haaslibs.Tensorflow;

@TeleOp
public class HardwareTest extends OpMode
{
    DcMotor motor;
    TouchSensor touchSensor;
    ColorSensor colorSensor;
    Servo servo;

    Tensorflow tensorflow;

    public void init()
    {
        //Init hardware
        motor = hardwareMap.get(DcMotor.class, "Motor");
        servo = hardwareMap.get(Servo.class, "Servo");
        touchSensor = hardwareMap.get(TouchSensor.class, "Touch_Sensor");
        colorSensor = hardwareMap.get(ColorSensor.class, "Color_Sensor");

        //Init webcam and CV models
        tensorflow.init(hardwareMap);
    }

    public void loop()
    {
        telemetry.addData("Right joystick", "Servo");
        telemetry.addData("Left joystick", "Motor");

        //print sensor info
        telemetry.addData("Touch Sensor", touchSensor.isPressed());
        telemetry.addData("Color Sensor Red", colorSensor.red());
        telemetry.addData("Color Sensor Blue", colorSensor.blue());
        telemetry.addData("Color Sensor Green", colorSensor.green());

        //Set motor and servo position
        motor.setPower(gamepad1.left_stick_x);
        servo.setPosition(gamepad1.right_stick_x);

        //Finally poll and print Gamepiece and April tag information
        Gamepiece gamepiece = tensorflow.pollTensorflow();
        AprilTag aprilTag = tensorflow.pollAprilTag();
        telemetry.addData("Gamepiece color", gamepiece.color);
        telemetry.addData("April tag", aprilTag.name);
    }

}