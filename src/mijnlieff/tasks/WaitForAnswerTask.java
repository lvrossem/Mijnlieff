package mijnlieff.tasks;

import javafx.concurrent.Task;
import mijnlieff.server.Client;

import java.io.IOException;

public class WaitForAnswerTask extends Task<String> {

    private Client client;

    public WaitForAnswerTask(Client client) {
        this.client = client;
    }

    @Override
    public String call() throws Exception {
        try {
            String answer = client.getBr().readLine();
            System.out.println(answer);
            return answer;
        } catch (IOException ex) {
            throw new RuntimeException("Onverwachte onderbreking", ex);
        }

    }
}
