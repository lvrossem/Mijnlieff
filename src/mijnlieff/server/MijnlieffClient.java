package mijnlieff.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Principal;


public abstract class MijnlieffClient {

    protected Socket socket;
    protected PrintWriter pw;
    protected BufferedReader br;

    public void closeConnection() {

        pw.close();

    }
}
