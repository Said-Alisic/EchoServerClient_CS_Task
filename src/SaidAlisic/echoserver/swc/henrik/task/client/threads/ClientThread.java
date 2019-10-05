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

            PrintWriter serverOut = new PrintWriter(activeSocket.getOutputStream(), true);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while(!activeSocket.isClosed()) {
                System.out.println("Write 'Disconnect' to terminate connecting.");

                String userIn;
                while((userIn = stdIn.readLine()) != null) {
                    serverOut.println(userIn);
                    serverOut.write(serverIn.readLine());
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}

