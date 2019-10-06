package SaidAlisic.echoserver.swc.henrik.task.server.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread implements Runnable {

    private String name;
    private Socket activeSocket;
    private String host;
    private int port;

    public ServerThread(String name, Socket activeSocket, String host, int port) {
        this.name = name;
        this.activeSocket = activeSocket;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        System.out.println(Thread.currentThread().getName() + " currently running.");

        String client;

        try {
            // Obtain input and output streams from active socket to communicate with client
            // Input stream of client is connected to output stream of server
            PrintWriter clientOut = new PrintWriter(activeSocket.getOutputStream(), true);
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));

            // Echo client input back to client side while the active socket is open
            while(!activeSocket.isClosed()) {
                System.out.println("Accepted client [" + activeSocket.getRemoteSocketAddress() + "] " +
                        "at socket [" + activeSocket.getLocalSocketAddress() + "]\n\n");

                client = clientIn.readLine();

                String userIn;
                while ((userIn = clientIn.readLine()) != null) {
                    clientOut.println("Client input at [(" + client + ")" + activeSocket.getLocalAddress() + "]: " + userIn);
                    System.out.println("Client input: " + userIn);

                    if(userIn.equalsIgnoreCase("Disconnect")) {
                        activeSocket.close();
                        System.out.println("Disconnected!");
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Could not accept client connection at host: [" + host + "] - port: [" + port + "]");
        }

    }
}
