// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import org.reflections.Reflections;

import java.util.*;

public class OpModeManager{
    public Map<Integer, Class> map = new HashMap<Integer, Class>();

    private ArrayList<Class> opModeClasses = new ArrayList<>();
    int j = 0;

    /*public void create() throws ClassNotFoundException {
        for (String opModeName : opModeNames) {
            opModeClasses[j] = Class.forName("YourCode." + opModeName);
            j += 1;
        }
    }*/

    public void register()
    {
        for (int i = 0; i < opModeClasses.size(); i++)
        {
            map.put(i, opModeClasses.get(i));
        }
    }

    public void Scan(){
        System.out.println("Scanning for OpModes:");

        Reflections ref = new Reflections("YourCode");
        for (Class<?> cl : ref.getTypesAnnotatedWith(RegisterOpMode.class)) {
            opModeClasses.add(cl);
            RegisterOpMode findable = cl.getAnnotation(RegisterOpMode.class);
            System.out.printf("Found class: %s, with meta name: %s%n",
                    cl.getSimpleName(), findable.name());
            j++;
        }
    }
}
