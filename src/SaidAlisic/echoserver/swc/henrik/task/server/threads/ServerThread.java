package SaidAlisic.echoserver.swc.henrik.task.server.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

        try {
            // Obtain input and output streams from active socket to communicate with client
            // Input stream of client is connected to output stream of server
            PrintWriter clientOut = new PrintWriter(activeSocket.getOutputStream(), true);
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));

            // Echo client input back to client side while the active socket is open
            while(!activeSocket.isClosed()) {
                clientOut.println("Successfully connected to " + name);
                clientOut.println("Client input at [" + activeSocket.getRemoteSocketAddress() + "]:" + clientIn.readLine());


                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String userInput;

                while ((userInput = stdIn.readLine()) != null) {
                    clientOut.println(userInput);
                    System.out.println("echo: " + clientIn.readLine());
                }

                if(clientIn.readLine().equalsIgnoreCase("Disconnect")) {
                    clientOut.close();
                    clientIn.close();
                    stdIn.close();
                    activeSocket.close();
                    System.out.println("Disconnected!");
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Could not accept client connection at host: [" + host + "] - port: [" + port + "]");
        }
    }
}
