package SaidAlisic.echoserver.swc.henrik.task.server.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StressThread implements Runnable {


    private Socket activeSocket;

    public StressThread(Socket activeSocket) {
        this.activeSocket = activeSocket;
    }


    @Override
    public void run() {
        try {
            // Connect stress client input and output streams to server IO streams
            PrintWriter serverOut = new PrintWriter(activeSocket.getOutputStream(), true);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));

            while(!activeSocket.isClosed()) {
                serverOut.println("Stress load");
                serverOut.println("To server: " + Thread.currentThread().getId());
                System.out.println(serverIn.readLine());
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
