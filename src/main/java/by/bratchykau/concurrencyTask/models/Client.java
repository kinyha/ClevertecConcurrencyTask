package by.bratchykau.concurrencyTask.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Client {
    private List<Integer> dataList;
    private List<Integer> responses;
    private int n;

    public Client(int n) {
        this.n = n;
        this.dataList = new ArrayList<>();
        this.responses = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            dataList.add(i);
        }
    }

    public void sendRequests(Server server) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(n);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int index = new Random().nextInt(dataList.size());
            int value = dataList.remove(index);
            Request request = new Request(value);
            Callable<Integer> task = () -> {
                int delay = new Random().nextInt(901) + 100;
                Thread.sleep(delay);
                int responseSize = server.processRequest(request);
                return responseSize;
            };
            Future<Integer> future = executorService.submit(task);
            futures.add(future);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        for (Future<Integer> future : futures) {
            int responseSize = future.get();
            responses.add(responseSize);
        }
    }

    public List<Integer> getResponses() {
        return responses;
    }

}

