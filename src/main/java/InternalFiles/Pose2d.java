// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

public class Pose2d {
    public double x;
    public double y;
    public double heading;

    public Pose2d(double x, double y, double heading)
    {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public String toString()
    {
        return ("(" + x + ", " + y + ", " + heading + ")");
    }

    public Vector3 vec()
    {
        return new Vector3(x, y);
    }
}
