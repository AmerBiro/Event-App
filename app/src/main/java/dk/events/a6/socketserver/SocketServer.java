package dk.events.a6.socketserver;

public class SocketServer {
    private int port;
    private SocketService service;
    private boolean running;

    public SocketServer(int port, SocketService service) {

        this.port = port;
        this.service = service;
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

    public void start() {
        running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }
}
