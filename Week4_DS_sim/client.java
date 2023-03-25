import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 50000);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            BufferedReader dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dout.write(("HELO\n").getBytes());
            dout.flush();

            dout.write(("AUTH xxx\n").getBytes());
            dout.flush(); 

            String str = (String) dis.readLine();
            System.out.println("message= " + str);
            dout.write(("REDY\n").getBytes());
            dout.flush();

            // str = (String) dis.readLine();
            // System.out.println("message= " + str);

            dout.write(("QUIT\n").getBytes());
            dout.flush();
            
            dout.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
