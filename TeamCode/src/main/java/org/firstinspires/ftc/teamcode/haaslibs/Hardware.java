package org.firstinspires.ftc.teamcode.haaslibs;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Hardware {
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    //Gyroscope
    IMU imu;

    //Track where the yaw was at the start, this is so the yaw can be reset
    double yawInitialValue;

    public Hardware (HardwareMap hwMap) {

        //Set Drive motors
        frontLeft = hwMap.get(DcMotor.class, "frontLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backLeft = hwMap.get(DcMotor.class, "backLeft");
        backRight = hwMap.get(DcMotor.class, "backRight");

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Reset and set gyroscope settings
        imu = hwMap.get(IMU.class, "imu");
        imu.resetYaw();
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(RevOrientation));
        imu.resetYaw();
        yawInitialValue = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

    }

    //Get heading
    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - yawInitialValue;
    }

    public void move(double speed, int ticksLeft, int ticksRight) {
        int calcTickLeft = (int) (Math.round(getTicks()) + (ticksLeft));
        int calcTickRight = (int) (Math.round(frontRight.getCurrentPosition() + (ticksRight)));
        frontRight.setTargetPosition(calcTickRight);
        backRight.setTargetPosition(calcTickRight);
        backLeft.setTargetPosition(calcTickLeft);
        frontLeft.setTargetPosition(calcTickLeft);

        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);

    }

    public void move(Movement movement) {
        int calcTickLeft = (int) (Math.round(getTicks()) + (movement.leftValue));
        int calcTickRight = (int) (Math.round(frontRight.getCurrentPosition() + (movement.rightValue)));
        frontRight.setTargetPosition(calcTickRight);
        backRight.setTargetPosition(calcTickRight);
        backLeft.setTargetPosition(calcTickLeft);
        frontLeft.setTargetPosition(calcTickLeft);

        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(movement.speed);
        frontRight.setPower(movement.speed);
        backLeft.setPower(movement.speed);
        backRight.setPower(movement.speed);

    }


    public double getTicks(){
        return frontLeft.getCurrentPosition();
    }

    public void turnToDegree(double degree) {
        double yaw = 10 * ( imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - yawInitialValue- degree) /50;
        double axial   = 0 /5;
        double lateral =  0/5;

        //Calc for wheels
        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral - yaw;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }


    public void setPower(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        frontLeft.setPower(frontLeftPower*-1);
        frontRight.setPower(frontRightPower*-1);
        backLeft.setPower(backLeftPower*-1);
        backRight.setPower(backRightPower*-1);
    }

    public void halt() {
        frontLeft.setPower(0.0);
        backLeft.setPower(0.0);
        frontRight.setPower(-0.0);
        backRight.setPower(-0.0);
    }
}