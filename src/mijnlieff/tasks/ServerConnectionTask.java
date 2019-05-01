package mijnlieff.tasks;

import javafx.concurrent.Task;
import mijnlieff.server.Client;

import java.io.IOException;

//een task die verbinding legt met de server
public class ServerConnectionTask extends Task<Client> {

    private String adres;
    private int poort;

    public ServerConnectionTask(String adres, int poort) {
        this.adres = adres;
        this.poort = poort;

    }

    public Client call() throws Exception {

        Client client;
        try {

            client = new Client(adres, poort);
            return client;
        } catch (Exception ex) {
            throw new RuntimeException("Onverwachte onderbreking", ex);
        }

    }
}
