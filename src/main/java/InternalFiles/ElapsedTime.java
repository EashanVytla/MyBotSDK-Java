// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

public class ElapsedTime {
    long strtt = 0;

    public void startTime()
    {
        strtt = System.currentTimeMillis();
    }

    public void reset()
    {
        strtt = System.currentTimeMillis();
    }

    public double timeSeconds()
    {
        return (System.currentTimeMillis() - strtt)/1000;
    }

    public double timeMinutes()
    {
        return (System.currentTimeMillis() - strtt)/60000;
    }

    public long timeMilliseconds()
    {
        return (System.currentTimeMillis() - strtt);
    }
}
