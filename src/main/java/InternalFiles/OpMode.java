// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

public abstract class OpMode
{
    public abstract void init();
    public abstract void loop();
    public void start() {};
    public void stop() {};
    public void init_loop(){}
    public Telemetry telemetry = null;
    public MecanumRobot Robot = null;

    public OpMode()
    {
        telemetry = new Telemetry();
        Robot = new MecanumRobot(telemetry);
    }

}
