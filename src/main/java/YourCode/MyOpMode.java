package YourCode;

import InternalFiles.*;
//import jdk.vm.ci.meta.Local;
//import sun.java2d.pipe.SpanShapeRenderer;

//If you want to be able to run the OpMode then you need this. The name is optional.
@RegisterOpMode(name = "Nihar")
//Make sure that you extend to OpMode to access the robot properly.
public class MyOpMode extends OpMode {
    //IMPORTANT: When running please click the DEBUG button NOT RUN!
    //See EXAMPLE_OpMode in the ExampleCode Package for more information
    Localizer localizer;
    Mecanum_Drive drive;

    enum State{
        CENTER,
        TOP_LEFT,
        BOTTOM_RIGHT,
        TOP_RIGHT,
        BOTTOM_LEFT
    }

    State mRobotState = State.CENTER;

    public void init() {
        localizer =  new Localizer(Robot);
        drive = new Mecanum_Drive(Robot, telemetry);
    }


    public void loop(){
        switch (mRobotState){
            case CENTER:
                mRobotState = State.TOP_LEFT;
                break;
            case TOP_LEFT:
                drive.goToPoint(new Pose2d(-30, 30, Math.PI/2), localizer.getPose());
                if(new Vector3(-30, 30).distanceToVector(new Vector3(localizer.getPose().x, localizer.getPose().y)) < 1.0){
                    mRobotState = State.BOTTOM_RIGHT;
                }
                break;
            case BOTTOM_RIGHT:

        }


        localizer.update();
    }
}