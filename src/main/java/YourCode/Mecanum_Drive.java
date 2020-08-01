package YourCode;

import InternalFiles.*;

import java.util.Arrays;


public class Mecanum_Drive {
    public MecanumRobot myrobot;

    public double kp = 0.03;
    public double ki = 0;
    public double kd = 0.0025;

    public double kpr = 0.6;
    public double kir = 0;
    public double kdr = 0.02;

    public PID_Controller _x_controller;
    public PID_Controller _y_controller;
    public PID_Controller _turn_controller;
    public Telemetry telemetry;


    public Mecanum_Drive(MecanumRobot robot, Telemetry telemetry){
        this.myrobot = robot;
        _x_controller = new PID_Controller(kp,ki,kd);
        _turn_controller = new PID_Controller(kpr,kir,kdr);
        _y_controller = new PID_Controller(kp,ki,kd);
        this.telemetry = telemetry;

    }

    public void setPower(double x, double y, double rot){
        double FrontLeftVal = y - x + rot;
        double FrontRightVal = y + x - rot;
        double BackLeftVal = y + x + rot;
        double BackRightVal = y - x - rot;

        double[] power = {FrontLeftVal, FrontRightVal, BackLeftVal, BackRightVal};
        Arrays.sort(power);

        if(power[3] > 1 ) {
            FrontLeftVal /= power[3];
            FrontRightVal /= power[3];
            BackLeftVal /= power[3];
            BackRightVal /= power[3];
        }

        myrobot.setPower(FrontLeftVal, BackLeftVal, FrontRightVal, BackRightVal);
    }

    public void setPowerCentric(double x, double y, double rot, double heading){
        Vector3 power = new Vector3(x,y);
        power.rotate(heading);
        telemetry.addData("Power: ", power.toString());
        setPower(power.x, power.y,rot);
    }

    public void goToPoint(Pose2d target, Pose2d current){
        double heading = current.heading;
        double target_heading = target.heading;

        if (current.heading >= Math.PI) {
            heading = -((2 * Math.PI) - current.heading);
        }

        if (Math.abs(target.heading - heading) >= Math.PI) {
            target_heading = -((2 * Math.PI) - target.heading);
        }

        double _x_power = _x_controller.update(target.x, current.x);
        double _y_power = _y_controller.update(target.y, current.y);
        double _rot_power = _turn_controller.update(target_heading, heading);

        setPowerCentric(-_x_power, _y_power, -_rot_power, current.heading);
    }
}