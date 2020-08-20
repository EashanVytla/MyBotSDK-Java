package YourCode;

import InternalFiles.*;

//If you want to be able to run the OpMode then you need this. The name is optional.
@RegisterOpMode
//Make sure that you extend to OpMode to access the robot properly.
public class MyOpMode extends OpMode {
    //IMPORTANT: When running please click the DEBUG button NOT RUN!

    //See EXAMPLE_OpMode in the ExampleCode Package for more information
    //See Documentation at https://eashan-vytla.gitbook.io/mybotsdk/

    public void init() {
        //Code to initialize robot. Only runs once.
    }

    public void loop(){
        Robot.setPower(1, 0, 0);
    }
}