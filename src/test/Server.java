package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public interface ClientHandler {
        // define...
        public void communicator(InputStream in, OutputStream out);

    }

    volatile boolean stop;

    public Server() {
        stop = false;
    }


    private void startServer(int port, ClientHandler ch) {
        // implement here the server...
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);
            while (!stop) {
                Socket aclient = serverSocket.accept();
                InputStream is = aclient.getInputStream();
                OutputStream os = aclient.getOutputStream();
                ch.communicator(is, os);
                aclient.close();
            }
            serverSocket.close();
        } catch (Exception e) {

        }
    }

    // runs the server in its own thread
    public void start(int port, ClientHandler ch) {
        new Thread(() -> startServer(port, ch)).start();
    }

    public void stop() {
        stop = true;
    }
}
