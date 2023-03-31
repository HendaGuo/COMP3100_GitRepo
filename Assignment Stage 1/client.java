import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 50000);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send HELO
            dout.write(("HELO\n").getBytes());
            dout.flush();
            
            // Receive OK
            String str = (String) din.readLine();

            // Send AUTH username
            dout.write(("AUTH xxx\n").getBytes());
            dout.flush(); 

            // Receive OK
            str = (String) din.readLine();
            System.out.println("message= " + str);
            dout.write(("REDY\n").getBytes());
            dout.flush();
            
            //Week 5
            str = (String) din.readLine();
            System.out.println("message= " + str);
            int jobID = Integer.parseInt(str.split(" ")[2]);

            dout.write(("GETS All\n").getBytes());
            dout.flush(); 

            str = (String) din.readLine();
            System.out.println("message= " + str);
            int nserver = Integer.parseInt(str.split(" ")[1]);

            dout.write(("OK\n").getBytes());
            dout.flush(); 
            
            int pre_core = 0;
            String serverType = "";
            int serverID = 0;
            for(int i=0; i <nserver; i++){
                str = (String) din.readLine(); //read \n everytime its looped
                System.out.println("message= " + str);
                String temp[] = str.split(" ");
                int ncore = Integer.parseInt(temp[4]);
                
                if(ncore>=pre_core){
                    serverType = (temp[0]);
                    serverID = Integer.parseInt(temp[1]);
                }
            }
            // schd 0, servery type, server if
            // keep track of job id
            // server type which is in variable
            // server is 0

            dout.write(("OK\n").getBytes());
            dout.flush(); 

            dout.write((String.format("SCHD %d %s %d\n", jobID, serverType, serverID)).getBytes());
            dout.flush(); 

            dout.write(("QUIT\n").getBytes());
            dout.flush();
            
            dout.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
