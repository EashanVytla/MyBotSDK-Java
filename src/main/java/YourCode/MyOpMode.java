// Eashan Vytla
// Purpose: This program serves as a template for users to start programming Iterative OpModes

package YourCode;

import InternalFiles.*;

//If you want to be able to run the OpMode then you need this. The name is optional.
@RegisterOpMode
//Make sure that you extend to OpMode to access the robot properly.
public class MyOpMode extends OpMode {
    //todo: IMPORTANT: When running please click the DEBUG button NOT RUN!
    // ^^ This is necessary for a graceful stop of the connection for the next run

    //See EXAMPLE_OpMode in the ExampleCode Package for more information
    //See Documentation at https://eashan-vytla.gitbook.io/mybotsdk/

    ElapsedTime time;

    public void init() {
        //Code to initialize robot. Only runs once.
        time = new ElapsedTime();
    }

    public void start(){
        time.startTime();
    }

    public void loop(){
        //Please run on debug mode
        Robot.setPower(1.0, 0 , 0);

        telemetry.addData("Position", Robot.getPose());
        telemetry.addData("Time", time.timeSeconds());
    }
}