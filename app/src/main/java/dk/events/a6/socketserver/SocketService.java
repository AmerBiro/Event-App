package dk.events.a6.socketserver;

import java.net.Socket;

public interface SocketService {

    void serve(Socket socket);
}
