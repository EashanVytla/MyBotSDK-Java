// Eashan Vytla
// Purpose: This program serves as a template for users to start programming Linear OpModes

package YourCode;

import InternalFiles.ElapsedTime;
import InternalFiles.LinearOpMode;
import InternalFiles.RegisterOpMode;

//If you want to be able to run the OpMode then you need this. The name is optional.
@RegisterOpMode
//Make sure that you extend to OpMode to access the robot properly.
public class MyLinearOpMode extends LinearOpMode {
    //todo: IMPORTANT: When running please click the DEBUG button NOT RUN!
    // ^^ This is necessary for a graceful stop of the connection for the next run

    //See EXAMPLE_OpMode in the ExampleCode Package for more information
    //See Documentation at https://eashan-vytla.gitbook.io/mybotsdk/

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.startTime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Send powers to wheels - (x, y, theta)
            // You can also send powers to wheels: (w1, w2, w3, w4) ~ see documentation
            Robot.setPower(1.0, 0.0, 0.0);

            // Show the elapsed game time
            telemetry.addData("Run Time", String.valueOf(runtime.timeSeconds()));
            telemetry.update();
        }
    }
}
