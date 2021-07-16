package InternalFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Form {
    private JButton StrtBtn;
    private JPanel mainpanel;
    private JComboBox OpModeDrp;
    private JList TelemetryLst;
    private Timer timer1;
    protected static int i;
    protected static boolean runningLoop = true;
    protected static boolean stopper = false;
    private static String fullTelemetry;

    private static JFrame frame = new JFrame(("MyBot Robot Controller"));

    OpModeManager mngr = new OpModeManager();

    protected static OpMode adaptiveOpMode = null;

    enum State{
        STATE_INIT,
        STATE_START,
        STATE_STOP
    }

    protected static State mAppState = State.STATE_INIT;

    public Form() {
        StrtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(mAppState == State.STATE_INIT){
                    if(OpModeDrp.getSelectedIndex() != -1)
                    {
                        adaptiveOpMode = (OpMode)CreateInstance((mngr.map.get(OpModeDrp.getSelectedIndex())));
                        if(System.getProperty("os.name").startsWith("Windows")){
                            adaptiveOpMode.Robot.msngr.setupIPWindows();
                        }else {
                            adaptiveOpMode.Robot.msngr.setupIPMac();
                        }
                        adaptiveOpMode.Robot.msngr.StartClient("v1.5,");
                        adaptiveOpMode.Robot.msngr.StartClient("start,");
                        adaptiveOpMode.init();
                        adaptiveOpMode.Robot.setPower(0, 0, 0);
                        StrtBtn.setText("Start");
                        mAppState = State.STATE_START;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Please select an OpMode to run");
                    }
                }else if(mAppState == State.STATE_START){
                    StrtBtn.setText("Stop");
                    runningLoop = true;
                    adaptiveOpMode.start();
                    new Thread(new RunLoop()).start();
                    new Thread(new TelemetryRunnable(TelemetryLst)).start();
                    mAppState = State.STATE_STOP;
                }else if(mAppState == State.STATE_STOP){
                    try {
                        Thread.sleep(200);
                        stopper = true;
                        adaptiveOpMode.stop();
                        Thread.sleep(200);
                        System.out.println("Safely Stopped");
                        runningLoop = false;
                        adaptiveOpMode.Robot.msngr.stop();
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                mngr.Scan();
                mngr.register();
                for ( Map.Entry<Integer, Class> kvp : mngr.map.entrySet() )
                {
                    if(kvp != null){
                        OpModeDrp.addItem(kvp.getKey() + " - " + kvp.getValue().getSimpleName());
                    }
                }
            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    if(mAppState == State.STATE_STOP){
                        Thread.sleep(200);
                        stopper = true;
                        adaptiveOpMode.stop();
                        System.out.println("Safely Stopped");
                        Thread.sleep(250);
                        runningLoop = false;
                        adaptiveOpMode.Robot.msngr.stop();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
    }

    private static Object CreateInstance(Class type) {
        Constructor constr = null;
        System.out.println(type);
        try {
            constr = type.getConstructor();
            if ( constr == null){
                System.out.println("Null constructor");
            }
            Object instance = constr.newInstance();
            if(instance == null){
                System.out.println("Null Instance");
            }
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            JOptionPane.showMessageDialog(null, "OpMode Could Not Be Found: Please make sure that you put the right OpMode name into MyOpModeManager");
            return null;
        }
    }


    public static void main(String[] args) {
        frame.setContentPane(new Form().mainpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                try {
                    if(mAppState == Form.State.STATE_STOP){
                        Thread.sleep(200);
                        stopper = true;
                        adaptiveOpMode.stop();
                        System.out.println("Safely Stopped");
                        Thread.sleep(250);
                        runningLoop = false;
                        adaptiveOpMode.Robot.msngr.stop();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
