package mijnlieff.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client
{

    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private String message;


    public Client(String server, int poort) {

        message = "";

        try {
            socket = new Socket(server, poort);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            pw = new PrintWriter(socket.getOutputStream(), true);


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closeConnection() {

        pw.close();

    }


    public String getNewMove() {



        try {
            String message = "";

            pw.println("X");
            message = br.readLine();

            return message;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    return null;
    }

    public String checkName(String name) {
        try {
            String message = "";

            pw.println("I " + name);
            message = br.readLine();


            System.out.println(message);
            return message;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }


}
