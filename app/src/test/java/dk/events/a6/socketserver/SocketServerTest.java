package dk.events.a6.socketserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class SocketServerTest {

    private FakeSocketService fakeService;
    private SocketServer server;
    private int port;

    @Before
    public void beforeAll() throws IOException {
        port = 8042;
        fakeService = new FakeSocketService();
        server = new SocketServer(port, fakeService);
    }

    @After
    public void afterAll() throws IOException, InterruptedException {
        //server.stop();
    }

    @Test
    public void initialize(){
        assertEquals(port, server.getPort());
        assertEquals(fakeService, server.getService());
    }

    @Test
    public void start_stop_server() throws IOException, InterruptedException {
        server.start();
        assertEquals(true, server.isRunning());
        server.stop();
        assertEquals(false, server.isRunning());
    }

    @Test
    public void givenConnectionIsRequested_returnServerAcceptTheConnection() throws IOException, InterruptedException {
        server.start();

        new Socket("localhost", port); //connect to the server port and ip/localhost

        server.stop();
        assertEquals(1, fakeService.connections);
    }

    public class FakeSocketService implements SocketService {
        public int connections = 0;
        @Override
        public void serve(Socket socket) {
            ++connections;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}



