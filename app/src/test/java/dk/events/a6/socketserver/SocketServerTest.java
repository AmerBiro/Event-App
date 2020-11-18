package dk.events.a6.socketserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SocketServerTest {

    private SocketService service;
    private SocketServer server;
    private int port;

    @Before
    public void beforeAll(){
        port = 42;
        service = new FakeSocketService();
        server = new SocketServer(port, service);
    }

    @Test
    public void initialize(){
        assertEquals(port, server.getPort());
        assertEquals(service, server.getService());
    }

    @Test
    public void start_stop_server(){
        server.start();
        assertEquals(true, server.isRunning());
        server.stop();
        assertEquals(false, server.isRunning());
    }

    
}



