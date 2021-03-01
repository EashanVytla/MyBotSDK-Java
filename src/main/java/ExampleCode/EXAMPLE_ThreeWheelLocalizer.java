// Eashan Vytla
// Purpose: This program serves as an example of a three dead wheel localizer program using the odometry wheels on the robot

package ExampleCode;

import InternalFiles.MecanumRobot;
import InternalFiles.Pose2d;
import InternalFiles.Vector3;

public class EXAMPLE_ThreeWheelLocalizer {
    MecanumRobot myrobot;
    public Pose2d myPose = new Pose2d(0, 0, 0);
    double TRACK_WIDTH = 15.39;
    double prevstrafe = 0;
    double prevvert = 0;
    double prevheading;
    double STRAFE_WIDTH =4.231; //center of the robot to the perpendicular wheel

    public EXAMPLE_ThreeWheelLocalizer(MecanumRobot robot){
        myrobot = robot;
    }

    public Pose2d getPose(){
        return myPose;
    }

    public void update(){
        double vert = Math.round((myrobot.getLeftOdo() + myrobot.getRightOdo()) / 2);
        double dvert = vert - prevvert;
        double dtheta = getHeading() - prevheading;
        double dstrafe = ((myrobot.getStrafeOdo()) - prevstrafe)-(STRAFE_WIDTH*dtheta);

        prevstrafe = myrobot.getStrafeOdo();
        prevvert = vert;

        Vector3 offset = constantVeloTrack(dstrafe, prevheading, dvert, dtheta);
        myPose = new Pose2d(myPose.x + offset.x, myPose.y + offset.y, getHeading());

        prevheading = getHeading();
    }

    public Vector3 constantVeloTrack(double dstrafe, double prevheading, double dvert, double dtheta){
        double sinterm = 0;
        double costerm = 0;

        if(dtheta == 0){
            sinterm = 1.0 - dtheta * dtheta / 6.0;
            costerm = dtheta / 2.0;
        }else{
            sinterm = Math.sin(dtheta)/dtheta;
            costerm = (1 - Math.cos(dtheta))/dtheta;
        }

        Vector3 feildCentricOffset = new Vector3((dstrafe * sinterm) + (dvert * -costerm), (dstrafe * costerm) + (dvert * sinterm)).rotated(prevheading);
        return feildCentricOffset;
    }

    public Vector3 ktrack(double dstrafe, double prevheading, double dvert, double dtheta){
        double sinterm = Math.sin(dtheta);
        double costerm = Math.cos(dtheta);

        double xsinterm = sinterm * dvert;
        double xcosterm = costerm * dstrafe;
        double ysinterm = sinterm * dstrafe;
        double ycosterm = costerm * dvert;

        Vector3 feildCentricOffset = new Vector3(xsinterm + xcosterm, ysinterm + ycosterm);
        feildCentricOffset.rotate(prevheading);

        return feildCentricOffset;
    }

    public double getHeading() {
        return (angleWrap((myrobot.getRightOdo() - myrobot.getLeftOdo())/TRACK_WIDTH));
    }

    public double angleWrap(double angle){
        return (angle + (2 * Math.PI)) % (2 * Math.PI);
    }
}
