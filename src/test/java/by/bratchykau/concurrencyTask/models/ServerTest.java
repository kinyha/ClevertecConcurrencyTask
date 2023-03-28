package by.bratchykau.concurrencyTask.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void checkProcessRequest() throws InterruptedException {
        int n = 1;
        Server server = new Server(n);
        Request request = new Request(n);
        int responseSize = server.processRequest(request);
        assertEquals(1, responseSize);
        assertTrue(server.verifyResource());
    }

    @Test
    void checkVerifyResource() {
        int n = 5;
        Server server = new Server(n);
        IntStream.rangeClosed(1, n).forEach(server.sharedResource::add);
        assertTrue(server.verifyResource());
    }

    @Test
    void checkCalculateExpectedAccumulator() {
        assertEquals(1, Server.calculateExpectedAccumulator(1));
        assertEquals(3, Server.calculateExpectedAccumulator(2));
        assertEquals(10, Server.calculateExpectedAccumulator(4));
    }

    @Test
    void checkConcurrency() throws InterruptedException {
        Server server = new Server(3);
        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Integer> responses = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Request request = new Request(i);
            executorService.execute(() -> {
                try {
                    int responseSize = server.processRequest(request);
                    responses.add(responseSize);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        assertTrue(server.verifyResource());
        assertEquals(3, responses.size());
    }
}