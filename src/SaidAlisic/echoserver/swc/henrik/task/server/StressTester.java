package SaidAlisic.echoserver.swc.henrik.task.server;

import SaidAlisic.echoserver.swc.henrik.task.server.threads.StressThread;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class StressTester {

    private static final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {

        // Will run until stopped or connects to the EchoServer
        while (true) {
            try {
                Socket socket = new Socket("127.0.0.1", 8080);
                while (true) {
                    threadPool.execute(new StressThread(socket));

                }
            } catch (ConnectException ce) {
                ce.printStackTrace();
            }
        }
    }
}
