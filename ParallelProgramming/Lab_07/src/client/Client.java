package client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    private Socket socket;

    Client() throws IOException {
        connect();
    }

    private void connect() throws IOException {
        socket = new Socket("localhost", 2123);
    }

    public void registerUser(String name) throws IOException {
        Writer writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write(1);
        writer.write(name.length());
        writer.write(name);
        writer.flush();
    }

    public void disconnect() throws IOException {
        Writer writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write(2);
        writer.flush();
    }
}
