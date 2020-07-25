package mijnlieff.tasks;

import javafx.concurrent.Task;
import mijnlieff.server.Client;
import java.io.IOException;
import java.net.Socket;

// Een task die verbinding legt met de server
public class ServerConnectionTask extends Task<Socket> {

    private String adres;
    private int poort;

    public ServerConnectionTask(String adres, int poort) {
        this.adres = adres;
        this.poort = poort;

    }

    @Override
    public Socket call() throws Exception {

        Socket socket;
        try {
            socket = new Socket(adres, poort);
            return socket;
        } catch (IOException ex) {
            throw new RuntimeException("Onverwachte onderbreking", ex);
        }
    }
}
