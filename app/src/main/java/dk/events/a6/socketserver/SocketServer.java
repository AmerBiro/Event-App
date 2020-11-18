package dk.events.a6.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketServer {
    private int port;
    private SocketService service;
    private boolean running;
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public SocketServer(int port, SocketService service) throws IOException {
        this.port = port;
        this.service = service;
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(1);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SocketService getService() {
        return service;
    }

    public void setService(SocketService service) {
        this.service = service;
    }

    public void start() throws IOException {
        final Socket[] serviceSocket = new Socket[1];
        executorService.execute(()-> {
            try {
                serviceSocket[0] =  serverSocket.accept();
                service.serve(serviceSocket[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() throws IOException, InterruptedException {
        executorService.awaitTermination(400, TimeUnit.MILLISECONDS);
        serverSocket.close();
        running = false;
    }
}
