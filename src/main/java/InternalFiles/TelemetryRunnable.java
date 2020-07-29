// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import javax.swing.*;

import static InternalFiles.Form.adaptiveOpMode;
import static InternalFiles.Form.runningLoop;

public class TelemetryRunnable implements Runnable{
    JList TList;

    public TelemetryRunnable(JList list) {
        TList = list;
    }

    public void run() {
        while(runningLoop){
            adaptiveOpMode.telemetry.updateInternal(TList);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
