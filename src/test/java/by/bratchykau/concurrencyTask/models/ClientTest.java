package by.bratchykau.concurrencyTask.models;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void checkSendRequests() throws ExecutionException, InterruptedException {
        int n = 100;
        Client client = new Client(n);
        Server server = new Server(n);

        client.sendRequests(server);

        assertTrue(server.verifyResource());
    }

}