// Eashan Vytla
// 2/18/2021
// Purpose: This class is the abstract class that enables users to use a LinearOpMode as well

package InternalFiles;

public abstract class LinearOpMode extends OpMode{
    public LinearOpMode(){
        super();
    }

    //Puts the current thread to sleep for a bit as it has nothing better to do.
    public void idle() throws InterruptedException {
        Thread.sleep(200);
    }

    //Has the opMode been started?
    public boolean	isStarted(){
        return Form.mAppState != Form.State.STATE_INIT;
    }

    //Has the the stopping of the opMode been requested?
    public boolean	isStopRequested(){
        return Form.mAppState == Form.State.STATE_STOP;
    }

    //Answer as to whether this opMode is active and the robot should continue onwards.
    public boolean	opModeIsActive(){
        return Form.runningLoop;
    }

    //Override this method and place your code here.
    protected abstract void runOpMode();

    //Sleeps for the given amount of milliseconds, or until the thread is interrupted.
    public void	sleep(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    public void waitForStart(){
        while(Form.mAppState == Form.State.STATE_INIT){ }
    }

    //From the non-linear OpMode; do not override
    public final void start(){ }

    //From the non-linear OpMode; do not override
    public final void stop(){ }

    //From the non-linear OpMode; do not override
    public final void loop(){ }

    //From the non-linear OpMode; do not override
    public final void init_loop(){ }

    //From the non-linear OpMode; do not override
    public final void init(){ }
}
