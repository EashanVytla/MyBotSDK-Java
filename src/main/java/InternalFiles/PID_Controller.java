// Eashan Vytla
// 3/29/2020
// Purpose: This class is an in-build PID controller for users to easily write PID loops

package InternalFiles;

public class PID_Controller {
    private double kp;
    private double ki;
    private double kd;
    private double target;
    private double ierror;
    private long prevtime;
    private double preverror;

    public PID_Controller(double kp, double ki, double kd){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    public double error = 0;

    public double update(double target, double current){
        error = target - current;
        ierror += error;

        double deltatime = System.currentTimeMillis() - prevtime;
        prevtime = System.currentTimeMillis();

        double deltaerror = error - preverror;
        preverror = error;


        double _p_controller = kp * error;
        double _i_controller = ki * ierror * deltatime;
        double _d_controller = kd * (deltaerror);


        return _p_controller + _i_controller + _d_controller;
    }
}