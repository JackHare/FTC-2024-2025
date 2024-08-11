package org.firstinspires.ftc.teamcode.haaslibs;

public class Movement {
    int rightValue;
    int leftValue;
    double speed;

    public Movement(double speed, int leftValue, int rightValue) {
        this.speed = speed;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }
}