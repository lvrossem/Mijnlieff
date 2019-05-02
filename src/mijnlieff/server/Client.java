package mijnlieff.server;

import mijnlieff.pieces.Color;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


//regelt de communicatie tussen server en speler
public class Client {

    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private Color color;



    public BufferedReader getBr() {
        return br;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            pw = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    public void sendMove(String move) {
        pw.println(move);
    }

    public void checkName(String name) {

        pw.println("I " + name);

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


}
