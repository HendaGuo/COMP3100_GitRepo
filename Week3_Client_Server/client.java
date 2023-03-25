import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("HELO");
            dout.flush();

            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = (String) dis.readUTF();
            System.out.println("message= " + str);
            dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("BYE");
            dout.flush();

            str = (String) dis.readUTF();
            System.out.println("message= " + str);
            
            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
