// Eashan Vytla
// Purpose: This program serves as an example of an Autonomous using Finite State Machine

package ExampleCode;

import InternalFiles.ElapsedTime;
import InternalFiles.OpMode;

public class EXAMPLE_FSM_Intro extends OpMode {
    ElapsedTime time;

    private enum myenum{
        STATE_FORWARD,
        STATE_STOP,
        STATE_BACKWARD
    }
    myenum RobotState = myenum.STATE_FORWARD;

    public void init() {
        time = new ElapsedTime();
    }

    @Override
    public void start() {
        time.startTime();
    }

    public void loop() {
        switch (RobotState){
            case STATE_FORWARD:
                    Robot.setPower(0, 0.5, 0);
                    if(Robot.getLeftOdo() >= 72.0){
                        time.reset();
                        RobotState = myenum.STATE_STOP;
                    }
                break;
            case STATE_STOP:
                    Robot.setPower(0, 0, 0);
                    if(time.timeSeconds() >= 1.0){
                        RobotState = myenum.STATE_BACKWARD;
                    }
                break;
            case STATE_BACKWARD:
                    if(Robot.getLeftOdo() <= 0.0){
                        Robot.setPower(0, 0, 0);
                    }else{
                        Robot.setPower(0, -0.5, 0);
                    }
                break;
        }

        telemetry.addData("Left Odometry Wheel", String.valueOf(Robot.getLeftOdo()));

        telemetry.addData("State", RobotState.toString());
    }
}
