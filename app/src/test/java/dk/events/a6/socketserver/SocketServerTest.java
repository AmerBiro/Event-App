package dk.events.a6.socketserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class SocketServerTest {

    private ClosingSocketService fakeService;
    private SocketServer server;
    private int port;
    private ReadSocketService readFakeService;
    @Before
    public void beforeAll() throws IOException {
        port = 8042;
        fakeService = new ClosingSocketService();
        readFakeService = new ReadSocketService();
        server = new SocketServer(port, fakeService);
    }

    @After
    public void afterAll() throws IOException, InterruptedException {
        server.stop();
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
        synchronized (fakeService){fakeService.wait();}
        server.stop();
        assertEquals(1, fakeService.connections);
    }

    @Test
    public void givenMoreConnectionsIsRequested_returnServerAcceptsAllTheConnections() throws IOException, InterruptedException {
        server.start();

        new Socket("localhost", port); //connect to the server port and ip/localhost
        synchronized (fakeService){fakeService.wait();}
        new Socket("localhost", port); //connect to the server port and ip/localhost
        synchronized (fakeService){fakeService.wait();}
        server.stop();
        assertEquals(2, fakeService.connections);
    }
    public class ClosingSocketService implements SocketService {
        public int connections = 0;
        public String msg;

        @Override
        public void serve(Socket socket) {
            ++connections;
            try {
                socket.close();
                synchronized (this){
                    this.notify();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void given_return() throws IOException, InterruptedException {
        server.stop();
        server = new SocketServer(port, readFakeService);
        //server.setService(readFakeService);

        server.start();

        Socket socket = new Socket("localhost", port);

        OutputStream os = socket.getOutputStream();
        os.write("test\n".getBytes());

        synchronized (readFakeService){readFakeService.wait();}

        server.stop();

        assertEquals("test", readFakeService.msg);
    }

    public class ReadSocketService implements SocketService {
        public int connections = 0;
        public String msg;

        @Override
        public void serve(Socket socket) {
            ++connections;
            try {

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                msg = br.readLine();

                socket.close();
                synchronized (this){ this.notify(); }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







}



