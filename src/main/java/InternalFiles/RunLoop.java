// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;


import static InternalFiles.Form.adaptiveOpMode;
import static InternalFiles.Form.runningLoop;

public class RunLoop implements Runnable {
    public void run() {
        if(adaptiveOpMode instanceof LinearOpMode){
            System.out.println("Running a Linear OpMode");
            try{
                ((LinearOpMode)(adaptiveOpMode)).runOpMode();
            }catch(Throwable e){
                Form.mAppState = Form.State.STATE_STOP;
                System.out.println("\nUser code threw an uncaught exception!");
                System.out.println("\nMessage:\n" + e.getMessage());
                System.out.println("\nCause:\n" + e.getCause());
                System.out.println("\nStack Trace:");
                e.printStackTrace();

                try {
                    Thread.sleep(200);
                    Form.stopper = true;
                    adaptiveOpMode.Robot.setPower(0, 0, 0);
                    Thread.sleep(200);
                    System.out.println("Safely Stopped");
                    runningLoop = false;
                    adaptiveOpMode.Robot.msngr.stop();
                    System.exit(0);
                } catch (InterruptedException c) {
                    c.printStackTrace();
                }
            }
        }else{
            System.out.println("Running an Iterative OpMode");
            try{
                while (runningLoop)
                {
                    adaptiveOpMode.loop();
                }
            }catch(Throwable e){
                Form.mAppState = Form.State.STATE_STOP;
                System.out.println("\n\nUser code threw an uncaught exception!");
                System.out.println("\n\nMessage:\n" + e.getMessage());
                System.out.println("\n\nCause:\n" + e.getCause());
                System.out.println("\n\nStack Trace:\n" + e.getStackTrace());

                try {
                    Thread.sleep(200);
                    Form.stopper = true;
                    adaptiveOpMode.Robot.setPower(0, 0, 0);
                    adaptiveOpMode.stop();
                    Thread.sleep(200);
                    System.out.println("Safely Stopped");
                    runningLoop = false;
                    adaptiveOpMode.Robot.msngr.stop();
                    System.exit(0);
                } catch (InterruptedException c) {
                    c.printStackTrace();
                }
            }
        }
    }
}
