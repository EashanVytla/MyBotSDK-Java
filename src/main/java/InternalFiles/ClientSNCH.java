// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying
package InternalFiles;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Enumeration;

class ClientSNCH
{
    protected String recieved_message;
    protected Telemetry t;

    protected String[] odos = new String[4];

    protected double left = 0;
    protected double right = 0;
    protected double strafe = 0;
    protected double gyro = 0;
    protected Vector3 pose = new Vector3(0, 0);

/*    public double startLeft =0;
    public double startRight = 0;
    public double startStrafe = 0;
    public double startGyro = 0;
    public Vector3 startPose = new Vector3(0, 0);*/

    protected SocketAddress remoteEP;
    protected Socket sender;

    protected DataInputStream din;
    protected DataOutputStream dos;
    protected String ipstring;

    // Data buffer for incoming data.
    private byte[] bytes = new byte[1024];
    protected BufferedReader reader;
    protected boolean first = true;

    public ClientSNCH(Telemetry telemetry)
    {
        t = telemetry;
        left = 0;
        right = 0;
        strafe = 0;
        pose = new Vector3(0, 0);
    }

    private String getCurrentIpWindows()
    {
        try
        {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c route print");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                line = line.trim();
                String [] tokens = line.split(" +");
                if(tokens.length == 5 && tokens[0].equals("0.0.0.0"))
                {
                    return tokens[3];
                }
            }
            //pro.waitFor();
        }
        catch(IOException e)
        {
            System.err.println(e);
            e.printStackTrace();
        }
        return null;
    }

    protected InetAddress getCurrentIp(){
        try{
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while(networkInterfaces.hasMoreElements()){
                NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while(nias.hasMoreElements()){
                    InetAddress ia = (InetAddress) nias.nextElement();
                    if(!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address){
                        return ia;
                    }
                }
            }
        }catch(SocketException e){
            e.printStackTrace();
        }
        return null;
    }

    protected void setupIPMac(){
        ipstring = getCurrentIp().getHostAddress();
        System.out.println(getCurrentIp().getHostAddress());
    }

    protected void setupIPWindows(){
        ipstring = getCurrentIpWindows();
    }

    protected void StartClient(String message)
    {
        // Data buffer for incoming data.
        byte[] bytes = new byte[1024];

        // Connect to a remote device.
        try
        {
            // Establish the remote endpoint for the socket.
            // I used port 8719 on the local computer.
            /*do{
                InetAddress address = InetAddress.getLocalHost();

                if(!address.isLoopbackAddress()){
                    remoteEP = new InetSocketAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostName()).getHostAddress(), 8719);
                    break;
                }
            }while(remoteEP == null);*/

            if(first){
                remoteEP = new InetSocketAddress(InetAddress.getByName("127.0.0.1").getHostAddress(), 8719);
                System.out.println("MyBot ~ Connected to the target Virtual Robot, address: " + remoteEP);
                sender = new Socket(InetAddress.getByName("127.0.0.1").getHostAddress(), 8719);
                sender.setKeepAlive(true);
            }

            first = false;

            din = new DataInputStream(new BufferedInputStream(sender.getInputStream()));
            dos = new DataOutputStream(sender.getOutputStream());

            // Connect the socket to the remote endpoint. Catch any errors.
            try
            {
                if(!sender.isConnected()){
                    sender.connect(remoteEP);
                }

                // Encode the data string into a byte array.
                byte[] msg = message.getBytes();

                if(Form.stopper){
                    message = "stop,";
                    msg = message.getBytes();
                }

                // Send the data through the socket.
                dos.write(msg);

                if(!Form.stopper){
                    // Receive the response from the remote device.
                    din.read(bytes);
                    recieved_message = new String(bytes);
                    parse(recieved_message);
                }
            }
            catch (IllegalArgumentException ane)
            {
                JOptionPane.showMessageDialog(null, "ArgumentNullException : " + ane.toString());
            }
            catch (SocketException se)
            {
                JOptionPane.showMessageDialog(null,"SocketException : " + se.toString());
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Unexpected exception : " + e.toString());
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }

    protected void stop(){
        try {
            sender.close();
            din.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //public boolean firstParse = true;
    //public boolean secondParse = false;

    protected void parse(String recieved)
    {
        odos = recieved.split(",");

        if (odos[0].equals("O"))
        {
            if (odos[1].equals("G"))
            {
                if(odos[2].equals("P"))
                {
                    //secondParse = true;
                    left = Double.parseDouble(odos[3]);
                    right = Double.parseDouble(odos[4]);
                    strafe = Double.parseDouble(odos[5]);
                    pose = new Vector3(Double.parseDouble(odos[6]), Double.parseDouble(odos[7]));
                    gyro = Double.parseDouble(odos[8]);
                }
                else
                {
                    left = Double.parseDouble(odos[2]);
                    right = Double.parseDouble(odos[3]);
                    strafe = Double.parseDouble(odos[4]);
                    gyro = Double.parseDouble(odos[5]);
                }
            }
            else if(odos[1].equals("P"))
            {
                left = Double.parseDouble(odos[2]);
                right = Double.parseDouble(odos[3]);
                strafe = Double.parseDouble(odos[4]);
                pose = new Vector3(Double.parseDouble(odos[5]), Double.parseDouble(odos[6]));
            }
            else
            {
                left = Double.parseDouble(odos[1]);
                right = Double.parseDouble(odos[2]);
                strafe = Double.parseDouble(odos[3]);
            }
        }
        else if (odos[0].equals("G"))
        {
            gyro = Double.parseDouble(odos[1]);
        }else if(odos[0].equals("P"))
        {
            if(odos[1].equals("G"))
            {
                pose = new Vector3(Double.parseDouble(odos[2]), Double.parseDouble(odos[3]));
                gyro = Double.parseDouble(odos[4]);
            }
            else
            {
                pose = new Vector3(Double.parseDouble(odos[1]), Double.parseDouble(odos[2]));
            }
        }
        else if (recieved.equals("stop"))
        {
            stop();
            System.exit(0);
        }

        /*if(secondParse){
            startGyro = gyro;
            startLeft = left;
            startStrafe = strafe;
            startRight = right;
            startPose = pose;

            firstParse = false;
        }*/
    }
}
