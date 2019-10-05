package SaidAlisic.echoserver.swc.henrik.task;

import SaidAlisic.echoserver.swc.henrik.task.server.EchoServer;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner SCN = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        EchoServer echoServer = new EchoServer();
        echoServer.setServerAttrs(SCN);
        echoServer.startServer();

    }
}
