package SaidAlisic.echoserver.swc.henrik.task.server;

import SaidAlisic.echoserver.swc.henrik.task.server.threads.ServerThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EchoServer {

    private String name;
    private String host;
    private int port;

    // Thread Pool with a specified maximum number of threads running concurrently
    private static final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    public void startServer() throws IOException {

        // Server runs on a specific computer with a socket bound to a specific port
        ServerSocket serverSocket = new ServerSocket();

        // Create socket address object
        InetSocketAddress endPoint = new InetSocketAddress(host, port);

        // Bind Server Socket to socket address object
        // queue-connection with a specified maximum number of clients in que
        serverSocket.bind(endPoint, 10);

        // Listen to the socket to check if any client hs made a connection request
        Socket activeSocket = serverSocket.accept();

        // Start server thread
        threadPool.execute(new ServerThread(name, activeSocket, host, port));

    }

    public void setServerAttrs(Scanner scn) {

        System.out.println("Please type in the Echo Server details.");
        System.out.print("Server name: ");
        this.name = scn.nextLine();
        System.out.print("Host: ");
        this.host = scn.nextLine();
        System.out.print("Port: ");
        this.port = scn.nextInt();
        System.out.println("Server attributes saved");
        scn.nextLine();

    }



}
