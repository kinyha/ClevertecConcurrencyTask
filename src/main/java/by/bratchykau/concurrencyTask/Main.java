package by.bratchykau.concurrencyTask;

import by.bratchykau.concurrencyTask.models.Client;
import by.bratchykau.concurrencyTask.models.Server;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {
    //main like integration test
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int n = 100;
        Server server = new Server(n);
        Client client = new Client(n);
        client.sendRequests(server);
        List<Integer> responses = client.getResponses();
        if (!server.verifyResource()) {
            System.out.println("Error: server resource is incorrect");
        }
        int accumulator = 0;
        for (int responseSize : responses) {
            accumulator += responseSize;
        }
        int expectedAccumulator = Server.calculateExpectedAccumulator(n);
        if (accumulator != expectedAccumulator) {
            System.out.println("Error: accumulator is incorrect");
        }
        System.out.println("Success: accumulator is " + accumulator);
    }
}
