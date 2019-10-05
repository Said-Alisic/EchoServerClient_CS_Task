package SaidAlisic.echoserver.swc.henrik.task.client;

import SaidAlisic.echoserver.swc.henrik.task.client.threads.ClientThread;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    private static String name;
    private static String host;
    private static int port;

    private static final Scanner SCN = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        setClientAttr();
        connectToServer();

    }

    private static void connectToServer() throws IOException {
        Socket activeSocket = new Socket(host, port);

        ClientThread clientThread = new ClientThread(name, activeSocket);

        Thread thread = new Thread(clientThread);
        thread.start();

    }

    private static void setClientAttr() {
        System.out.println("Please type in the Echo Client details.");
        System.out.print("Client name: ");
        name = SCN.nextLine();
        System.out.print("Which host to connect to: ");
        host = SCN.nextLine();
        System.out.print("Which port to connect to: ");
        port = SCN.nextInt();
        System.out.println("Press any key to continue.");
        SCN.nextLine();
    }



}
