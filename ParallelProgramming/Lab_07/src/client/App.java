package client;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {

        Client client = new Client();

        System.out.print("User name: ");
        Scanner scanner = new Scanner(System.in);

        String playerName = scanner.next();
        client.registerUser(playerName);

        for (int i = 0; i < 10; ++i) {
            Question question = client.getQuestion(i);
            System.out.println(question.getQuestionStr());

            int j = 1;
            for (String answer : question.getAnswers()) {
                System.out.println(j++ + ": " + answer);
            }

            System.out.print("Your answer is: ");

            int answer = Integer.parseInt(scanner.next());
            client.registerAnswer(i, answer - 1);
            System.out.println();
        }


        Map<String, Integer> results = client.getResults();
        System.out.println("Results:");
        for (String name : results.keySet()) {
            System.out.println(name + " : " + results.get(name));
        }

        client.disconnect();
    }
}
