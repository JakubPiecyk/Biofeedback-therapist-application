package sample;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/*
This Class is used to make connection with RaspBerryPi application.
It uses TCP/IP protocol.
In this class i wrote methods to access data from pulse sensor, also there is method that sends "permission" to Raspberry to move a robot with specified speed (Commands : w, s, a, d)
Command "Max" order Raspberry pi to send back pulse vaule.
Command "q" closes client socked and order Raspberry Pi to close server socket.
 */

public class RaspConnect {





    private String HRate = "0";

    private int HR = 0;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private OutputStreamWriter outWrite;
    private BufferedReader buffer;

    private boolean isrunning;

    public void start() {

        try {
            //192.168.0.147
            this.socket = new Socket("192.168.43.125", 12345);
            this.isrunning = true;

        } catch (UnknownHostException e) {
            this.isrunning = false;
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
        try {
            this.in = this.socket.getInputStream();
            this.out = this.socket.getOutputStream();
            this.outWrite = new OutputStreamWriter(socket.getOutputStream());
            this.buffer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isrunning() {
        return isrunning;
    }

    public void run(String sentense) {
        try {
            //Scanner scanner = new Scanner(System.in);
            if (this.socket.isConnected()) {

                //
                // System.out.print("Java: Enter data to send: ");
                //String dataToSend = scanner.nextLine();
                //
                if (sentense.equals("max")) {
                    int character;


                    this.outWrite.write(sentense);
                    this.outWrite.flush();

                    StringBuilder data = new StringBuilder();

                    while (true) {
                        character = this.buffer.read();
                        data.append((char) character);

                        if (!this.buffer.ready()) {

                            HRate = data.toString();
                            HR = Integer.parseInt(HRate);

                            System.out.println(HR);


                            data = data.delete(0, data.length());


                            break;
                        }

                    }
                }
                if (sentense.equals("q")) {
                    this.outWrite.write(sentense);
                    this.outWrite.flush();
                    this.socket.close();
                    this.isrunning = false;

                }
                if (sentense.equals("w")) {
                    this.outWrite.write(sentense);
                    this.outWrite.flush();

                }

                if (sentense.equals("s")) {
                    this.outWrite.write(sentense);
                    this.outWrite.flush();

                }
                if (sentense.equals("a")) {
                    this.outWrite.write(sentense);
                    this.outWrite.flush();

                }

                if (sentense.equals("d")) {
                    this.outWrite.write(sentense);
                    this.outWrite.flush();

                }

            } else System.out.println("No connection");
        } catch (IOException io) {
        }
    }

    public int getHR() {
        return HR;
    }

    public String getHRate() {
        return HRate;
    }

}