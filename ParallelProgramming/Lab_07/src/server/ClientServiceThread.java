package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

public class ClientServiceThread extends Thread {
    private User thisUser;
    private Socket socket;
    private Server server;


    public ClientServiceThread(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                processMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (socket.isClosed()) {
                interrupt();
            }
        }
    }

    private void processMessage() throws IOException {

        Reader reader = new InputStreamReader(socket.getInputStream());

        char[] requestTypeBuff = new char[1];
        reader.read(requestTypeBuff);

        int requestType = requestTypeBuff[0];
        switch (requestType) {
            case 1:
                logInUser(reader);
                break;
            case 2:
                socket.close();
                break;
        }
    }

    private void logInUser(Reader reader) throws IOException {
        char[] nameLength = new char[1];
        reader.read(nameLength);

        char[] nameBuff = new char[nameLength[0]];
        reader.read(nameBuff);

        String name = new String(nameBuff);
        thisUser = new User(name);
        server.logInUser(thisUser);
    }

    private void getUsersStatus(Reader reader) throws IOException {

        char[] questionIdBuf = new char[1];
        reader.read(questionIdBuf);

        int questionId = questionIdBuf[0];

        Question question = questionStorage.getQuestion(questionId);

        Writer writer = new OutputStreamWriter(socket.getOutputStream());

        String questionStr = question.getQuestionStr();
        int questionStrLength = questionStr.length();

        writer.write(questionStrLength);
        writer.write(questionStr);

        List<String> answers = question.getAnswers();
        writer.write(answers.size());

        for (String answer : answers) {
            writer.write(answer.length());
            writer.write(answer);
        }

        writer.flush();
    }
}
