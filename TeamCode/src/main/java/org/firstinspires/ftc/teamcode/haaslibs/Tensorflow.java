package org.firstinspires.ftc.teamcode.haaslibs;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

//Managse Custom tensorflow model
public class Tensorflow {

    //Toggle for Webcam, our robot uses true by default
    private boolean USE_WEBCAM = true;

    //CV processors
    private TfodProcessor myTfodProcessor;
    private AprilTagProcessor aprilTag;

    private VisionPortal myVisionPortal;

    //Prepare tensorflow
    public void init(HardwareMap hardwareMap) {

        //Tensorflow builder
        TfodProcessor.Builder myTfodProcessorBuilder;
        VisionPortal.Builder myVisionPortalBuilder;

        //Create an TFOD builder instance
        myTfodProcessorBuilder = new TfodProcessor.Builder();

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder().build();

        //Model file
        myTfodProcessorBuilder.setModelFileName("modelv4.tflite");

        //Set object names
        myTfodProcessorBuilder.setModelLabels(JavaUtil.createListWith("blue", "red"));

        //Set the aspect ratio for the images used when the model was created.
        myTfodProcessorBuilder.setModelAspectRatio(16 / 9);


        // Create a TfodProcessor by calling build.
        myTfodProcessor = myTfodProcessorBuilder.build();

        //Create a VisionPortal.Builder and set attributes related to the camera.
        myVisionPortalBuilder = new VisionPortal.Builder();

        //If a webcam is true
        if (USE_WEBCAM) {
            Size cameraSize = new Size(1280, 720);
            // myVisionPortalBuilder.setCameraResolution(cameraSize);
            myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        }

        //Add myTfodProcessor to the VisionPortal.Builder.
        myVisionPortalBuilder.addProcessor(myTfodProcessor);

        // Set and AprilTag enable the processor.
        myVisionPortalBuilder.addProcessor(aprilTag);

        // Create a VisionPortal by calling build.
        myVisionPortal = myVisionPortalBuilder.build();
    }

    //Polls gamepiece, position, size, and color
    public Gamepiece pollTensorflow() {

        //List of recognitions
        List<Recognition> myTfodRecognitions;
        Recognition myTfodRecognition;

        float x;
        float y;

        //Gamepiece object
        Gamepiece gamepiece = new Gamepiece(0,0,0,0, "");

        //Get a list of all objects
        myTfodRecognitions = myTfodProcessor.getRecognitions();

        //Look through each object
        for (Recognition myTfodRecognition_item : myTfodRecognitions) {

            //Get each individual object
            myTfodRecognition = myTfodRecognition_item;

            //Get the x, y position
            x = (myTfodRecognition.getLeft() + myTfodRecognition.getRight()) / 2;
            y = (myTfodRecognition.getTop() + myTfodRecognition.getBottom()) / 2;

            //Add gamepiece info to the gamepiece
            gamepiece.x = x;
            gamepiece.y = y;
            gamepiece.width = myTfodRecognition.getWidth();
            gamepiece.height = myTfodRecognition.getHeight();
            gamepiece.color = myTfodRecognition.getLabel();
        }

        //Return the result
        return gamepiece;
    }

    public AprilTag pollAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        AprilTag aprilTag = new AprilTag();
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                aprilTag.number = detection.id;
                aprilTag.name = detection.metadata.name;

                aprilTag.x = detection.ftcPose.x;
                aprilTag.y = detection.ftcPose.y;
                aprilTag.z = detection.ftcPose.z;
                aprilTag.pitch = detection.ftcPose.pitch;
                aprilTag.roll = detection.ftcPose.roll;
                aprilTag.yaw = detection.ftcPose.yaw;
                aprilTag.range = detection.ftcPose.range;
                aprilTag.bearing = detection.ftcPose.bearing;
                aprilTag.elevation = detection.ftcPose.elevation;
            }
        }   // end for() loop
        return aprilTag;
    }


}

