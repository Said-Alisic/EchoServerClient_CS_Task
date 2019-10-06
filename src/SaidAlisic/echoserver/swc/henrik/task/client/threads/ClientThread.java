package SaidAlisic.echoserver.swc.henrik.task.client.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {

    private String name;
    private Socket activeSocket;

    public ClientThread(String name, Socket activeSocket) {
        this.name = name;
        this.activeSocket = activeSocket;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        System.out.println(Thread.currentThread().getName() + " client thread is running!");

        try {

            // Connect client input and output streams to server IO streams
            PrintWriter serverOut = new PrintWriter(activeSocket.getOutputStream(), true);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(System.in));

            while(!activeSocket.isClosed()) {
                serverOut.println(name);
                System.out.println("\n\nWrite 'Disconnect' to terminate connection.\n\n");

                String userIn;
                System.out.print("Your message: ");
                while((userIn = clientIn.readLine()) != null) {
                    System.out.println("\n______________________________________");
                    serverOut.println(userIn);
                    System.out.println(serverIn.readLine());
                    System.out.print("Your message: ");

                    if(userIn.equalsIgnoreCase("Disconnect")) {
                        activeSocket.close();
                        System.out.println("Disconnected!");
                        break;
                    }
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}

