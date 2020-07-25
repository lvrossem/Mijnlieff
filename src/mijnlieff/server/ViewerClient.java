package mijnlieff.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ViewerClient extends MijnlieffClient {

    public ViewerClient(String server, int poort) {
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
}
