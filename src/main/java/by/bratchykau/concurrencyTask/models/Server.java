package by.bratchykau.concurrencyTask.models;

import by.bratchykau.concurrencyTask.models.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    List<Integer> sharedResource;
    private int n;

    private Lock lock;


    public Server(int n) {
        this.n = n;
        this.sharedResource = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public int processRequest(Request request) throws InterruptedException {
        int value = request.getValue();
        int delay = new Random().nextInt(901) + 100;
        Thread.sleep(delay);
        lock.lock();
        try {
            sharedResource.add(value);
            int responseSize = sharedResource.size();
            return responseSize;
        } finally {
            lock.unlock();
        }
    }

    public boolean verifyResource() {
        if (sharedResource.size() != n) {
            return false;
        }
        for (int i = 1; i <= n; i++) {
            if (!sharedResource.contains(i)) {
                return false;
            }
        }
        return true;
    }

    public static int calculateExpectedAccumulator(int n) {
        if (n == 1) {
            return 1;
        }
        return (1 + n) * (n / 2);
    }
}
