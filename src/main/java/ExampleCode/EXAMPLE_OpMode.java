// Eashan Vytla
// Purpose: This program serves as an example OpMode so users can kickstart their development using MyBot

package ExampleCode;

import InternalFiles.ElapsedTime;
import InternalFiles.OpMode;
import InternalFiles.RegisterOpMode;

//Registers the OpMode onto the virtual app.
@RegisterOpMode
public class EXAMPLE_OpMode extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        Robot.setPower(1.0, 0.0, 0.0, 1.0);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + String.valueOf(runtime.timeMilliseconds()));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
