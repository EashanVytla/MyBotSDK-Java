// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import static InternalFiles.Form.adaptiveOpMode;
import static InternalFiles.Form.runningLoop;

public class RunLoop implements Runnable {
    public void run() {
        while (runningLoop)
        {
            adaptiveOpMode.loop();
            Form.i += 1;
        }
    }
}
