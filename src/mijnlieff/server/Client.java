package mijnlieff.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

//regelt de communicatie tussen server en speler
public class Client {

    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;




    public Client(String server, int poort) {

        try {
            socket = new Socket(server, poort);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            pw = new PrintWriter(socket.getOutputStream(), true);


        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public BufferedReader getBr() {
        return br;
    }

    public void sendBoard(String board) {
        pw.println(board);
    }

    public void enterQueue() {
        pw.println("P");

    }

    public String challengePlayer(String player) {
        pw.println("C " + player);

        try {
            return br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         return null;
    }

    public void closeConnection() {

        pw.close();

    }

    public ArrayList<String> getOpponents() {
        ArrayList<String> opponents = new ArrayList<>();
        try {
            pw.println("W");


            String opponent = "";

            while (!opponent.equals("+")) {
                opponent = br.readLine();
                opponents.add(opponent);
                System.out.println(opponent);
            }

            return opponents;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return opponents;
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

    public void checkName(String name) {

        pw.println("I " + name);

    }


}
