// Eashan Vytla
// Purpose: This program serves as an example of a go-to-point using the in-build gotopoint() function in the Robot class

package ExampleCode;

import InternalFiles.OpMode;
import InternalFiles.Pose2d;

public class EXAMPLE_GO_TO_POINT extends OpMode {


    @Override
    public void init() {

    }

    @Override
    public void loop() {
        Robot.goToPoint(new Pose2d(35, 35, Math.PI/2), new Pose2d(Robot.getPose().x, Robot.getPose().y, Robot.getHeading()), 1.0, 1.0);
        telemetry.addData("Pose: ", String.valueOf(Robot.getPose()));
        telemetry.addData("left odo: ", String.valueOf(Robot.getPose()));
        telemetry.addData("right odo: ", String.valueOf(Robot.getPose()));
        telemetry.addData("strafe odo: ", String.valueOf(Robot.getPose()));
        telemetry.addData("heading radians: ", String.valueOf(Robot.getHeading()));
        telemetry.addData("heading degrees: ", String.valueOf(Robot.getHeadingDegrees()));
    }
}
