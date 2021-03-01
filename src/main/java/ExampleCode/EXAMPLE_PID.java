// Eashan Vytla
// Purpose: This program serves as an example of a custom PID controller

package ExampleCode;

import InternalFiles.OpMode;

public class EXAMPLE_PID extends OpMode {
    //There is also an in-build PID controller in the Robot class
    double kp = 0.04;
    double kd = 0.00;
    double power = 0.0;
    double odoleft = 0.0;
    double odoright = 0.0;
    double preverror = 0.0;

    public void init()
    {

    }

    public void loop()
    {
        odoleft = Robot.getLeftOdo();
        odoright = Robot.getRightOdo();

        double error = 72 - ((odoright + odoleft) / 2);
        preverror = error;

        power = (kp * error) + (kd * (error - preverror));

        Robot.setPower(0.0, power, 0.0);
        telemetry.addData("left: ", String.valueOf(odoleft));
        telemetry.addData("right: ", String.valueOf(odoright));
        telemetry.addData("Heading: ", String.valueOf(Robot.getHeading()));
        telemetry.addData("Pose: ", Robot.getPose().toString());
    }
}
