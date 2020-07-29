package InternalFiles;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;

import javax.swing.*;

public class MecanumRobot {
    protected boolean odoquery;
    protected boolean gyroquery;
    protected boolean posequery;
    protected boolean centric;

    protected static boolean leftreciever;
    protected static boolean rightreciever;
    protected static boolean strafereciever;
    protected static boolean gyroreciever;
    protected static boolean posereciever;

    PIDFController PID_X;
    PIDFController PID_Y;
    PIDFController PID_Z;

    private float kp = 0.03f;
    private float ki = 0;
    private float kd = 0.0025f;

    private float kpr = 0.6f;
    private float kir = 0;
    private float kdr = 0.02f;

    protected ClientSNCH msngr;

    public MecanumRobot(Telemetry telemetry) {
        msngr = new ClientSNCH(telemetry);

        odoquery = false;
        gyroquery = false;
        centric = false;

        PID_X = new PIDFController(new PIDCoefficients(kp, ki, kd));
        PID_Y = new PIDFController(new PIDCoefficients(kp, ki, kd));
        PID_Z = new PIDFController(new PIDCoefficients(kpr, kir, kdr));

        leftreciever = false;
        rightreciever = false;
        strafereciever = false;
        gyroreciever = false;
    }

    //Make sure that this is only called once in the OpMode. Preferably in init() but it is user preference.
    public void setGoToPointPIDCoeff(float kpTrans, float kiTrans, float kdTrans, float kpRot, float kiRot, float kdRot) {
        kp = kpTrans;
        ki = kiTrans;
        kd = kdTrans;

        kpr = kpRot;
        kir = kiRot;
        kdr = kdRot;

        PID_X = new PIDFController(new PIDCoefficients(kp, ki, kd));
        PID_Y = new PIDFController(new PIDCoefficients(kp, ki, kd));
        PID_Z = new PIDFController(new PIDCoefficients(kpr, kir, kdr));
    }

    public double RangeClip(double value, double min, double max) {
        if (value >= max) {
            return max;
        } else if (value <= min) {
            return min;
        } else {
            return value;
        }
    }

    private void sendPower(double ul, double bl, double ur, double br) {
        /////Sending the packages
        if (odoquery && !Form.stopper/* || msngr.firstParse*/) {
            msngr.StartClient("O,");
            odoquery = false;
        } else if (gyroquery && !Form.stopper/* || msngr.firstParse*/) {
            msngr.StartClient("G,");
            gyroquery = false;
        } else if (posequery && !Form.stopper/* || msngr.firstParse*/) {
            msngr.StartClient("P,");
            posequery = false;
        } else {
            msngr.StartClient("rp" + RangeClip(ul, -1.0f, 1.0f) + "|" + RangeClip(ur, -1.0f, 1.0f) + "|" + RangeClip(bl, -1.0f, 1.0f) + "|" + RangeClip(br, -1.0f, 1.0f) + ",");
        }
    }

    public void setPower(double UpLeft, double BackLeft, double UpRight, double BackRight) {
        centric = false;
        if (!Double.isNaN(UpLeft) &&
                !Double.isNaN(BackLeft) &&
                !Double.isNaN(UpRight) &&
                !Double.isNaN(BackRight) &&
                Double.isFinite(UpLeft) &&
                Double.isFinite(BackLeft) &&
                Double.isFinite(UpRight) &&
                Double.isFinite(BackRight)) {
            sendPower(UpLeft, BackLeft, UpRight, BackRight);
        }else{
            JOptionPane.showMessageDialog(null, "Error: Infinite or NaN power set.");
        }
    }

    public void setPower(double x, double y, double rot) {
        double FrontLeftVal = y - x + rot;
        double FrontRightVal = y + x - rot;
        double BackLeftVal = y + x + rot;
        double BackRightVal = y - x - rot;

        setPower(FrontLeftVal, BackLeftVal, FrontRightVal, BackRightVal);
    }

    private boolean first = true;
    private boolean firstg = true;
    private boolean firstp = true;


    public double getLeftOdo() {
        if (first) {
            odoquery = true;
            leftreciever = true;
            first = false;
        }
        return msngr.left * 2.11143889 * 1.07527431;
    }

    public double getRightOdo() {
        if (first) {
            odoquery = true;
            first = false;
            rightreciever = true;
        }
        return msngr.right * 2.11143889 * 1.07527431;
    }

    public double getStrafeOdo() {
        if (first) {
            odoquery = true;
            strafereciever = true;
            first = false;
        }
        return msngr.strafe * 2.11143889 * 1.07527431;
    }

    public void setPowerCentic(double x, double y, double rot, double heading) {
        setPower(new Vector3(x, y).rotated(-heading), rot);
    }

    public void setPower(Vector3 vec, double rot) {
        setPower(vec.x, vec.y, rot);
    }

    public void goToPoint(Pose2d targetPos, double maxmovespeed, double maxturnspeed) {
        goToPoint(targetPos, new Pose2d(getPose().x, getPose().y, getHeading()), maxmovespeed, maxturnspeed);
    }

    public void goToPoint(double target_x, double target_y, double target_heading, double maxmovespeed, double maxturnspeed) {
        goToPoint(new Pose2d(target_x, target_y, target_heading), new Pose2d(getPose().x, getPose().y, getHeading()), maxmovespeed, maxturnspeed);
    }

    public void goToPoint(Pose2d targetPos, Pose2d currentPos, double maxmovespeed, double maxturnspeed) {
        PID_X.setOutputBounds(-maxmovespeed, maxmovespeed);
        PID_Y.setOutputBounds(-maxmovespeed, maxmovespeed);
        PID_Z.setOutputBounds(-maxturnspeed, maxturnspeed);

        double heading = 0;
        double target_heading = targetPos.heading;

        if (currentPos.heading <= Math.PI) {
            heading = currentPos.heading;
        } else {
            heading = -((2 * Math.PI) - currentPos.heading);
        }

        if (Math.abs(targetPos.heading - heading) >= Math.toRadians(180.0)) {
            target_heading = -((2 * Math.PI) - targetPos.heading);
        }

        PID_X.setTargetPosition(targetPos.x);
        PID_Y.setTargetPosition(targetPos.y);
        PID_Z.setTargetPosition(target_heading);

        double headingpower = PID_Z.update(heading);

        setPowerCentic(-PID_X.update(currentPos.x), PID_Y.update(currentPos.y), headingpower, currentPos.heading);
    }

    public void goToPoint(double target_x, double target_y, double target_heading, double currentPos_x, double currentPos_y, double currentHeading, double maxmovespeed, double maxturnspeed) {
        goToPoint(new Pose2d(target_x, target_y, target_heading), new Pose2d(currentPos_x, currentPos_y, currentHeading), maxmovespeed, maxturnspeed);
    }

    public Vector3 getPose() {
        if (firstp) {
            posequery = true;
            posereciever = true;
            firstp = false;
        }
        return new Vector3(msngr.pose.x, msngr.pose.y);
    }

    //Returns the heading in degrees
    public double getHeadingDegrees() {
        if (firstg) {
            gyroquery = true;
            gyroreciever = true;
            firstg = false;
        }
        return msngr.gyro;
    }

    //Returns the heading in radians
    public double getHeading() {
        if (firstg) {
            gyroquery = true;
            gyroreciever = true;
            firstg = false;
        }
        return Math.toRadians(msngr.gyro);
    }
}