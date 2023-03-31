import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            /*
             * Step 1: reate a socket
             * Step 2: Initialise input and output streams associated with the socket
             * Step 3: Connect ds-server
             */
            Socket socket = new Socket("localhost", 50000);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Step 4: Send HELO
            dout.write(("HELO\n").getBytes());
            dout.flush();

            // Step 5: Receive OK
            String str = (String) din.readLine();

            // Step 6: Send AUTH username
            String username = System.getProperty("user.name");
            dout.write(("AUTH " + username + "\n").getBytes());
            dout.flush();

            // Step 7: Receive OK
            str = (String) din.readLine();
            System.out.println("message= " + str);

            // Step 8
            while (!str.equals("NONE")) {

                // Step 9: Send REDY
                dout.write(("REDY\n").getBytes());
                dout.flush();

                // Step 10: Receive JOBN & Identify the largest server type
                // check if JOBN/JCPL
                str = (String) din.readLine();
                System.out.println("message= " + str);
                String[] job = str.split(" ");

                if (job[0].equals("JOBN")) {
                    int jobID = Integer.parseInt(str.split(" ")[2]);

                    // Step 11: Send a GETS message
                    dout.write(("GETS All\n").getBytes());
                    dout.flush();

                    // Step 12: Receive DATA nRecs recSize
                    str = (String) din.readLine();
                    System.out.println("message= " + str);
                    int nserver = Integer.parseInt(str.split(" ")[1]);

                    // Step 13: Send OK
                    dout.write(("OK\n").getBytes());
                    dout.flush();

                    // Step 14
                    int pre_core = 0;
                    String serverType = "";
                    int serverID = 0;
                    for (int i = 0; i < nserver; i++) {
                        str = (String) din.readLine(); // read \n everytime its looped
                        System.out.println("message= " + str);
                        String temp[] = str.split(" ");
                        int ncore = Integer.parseInt(temp[4]);

                        if (ncore >= pre_core) {
                            serverType = (temp[0]);
                            serverID = Integer.parseInt(temp[1]);
                        }
                    }

                    // Step 18: Send OK
                    dout.write(("OK\n").getBytes());
                    dout.flush();

                    // Step 19: Receive .
                    str = (String) din.readLine();
                    System.out.println("message= " + str);

                    // Step 20: Schedule a job (IF JOBN -> SCHD)
                    dout.write((String.format("SCHD %d %s %d\n", jobID, serverType, serverID)).getBytes());
                    dout.flush();

                    // Receive OK
                    str = (String) din.readLine();
                    System.out.println("message= " + str);
                }
            }

            // Step 24: Send QUIT
            dout.write(("QUIT\n").getBytes());
            dout.flush();

            // Step 24: Receive QUIT
            str = (String) din.readLine();
            System.out.println("message= " + str);

            // Step 26: Close the socket
            dout.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}