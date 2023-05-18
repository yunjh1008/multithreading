import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Worker implements Runnable {
    private int id;

    public Worker(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task with id " + id + " is in work - thread id: " + Thread.currentThread().getId());
        long duration = (long) (Math.random() * 5);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            // To guarantee 100 times executed
            Thread.currentThread().interrupt();
        }
    }
}

class FixedThreadPool {
    public static void main(String[] args) {
        // It is a single thread that will execute the tasks sequentially
        // so one after another
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 100; i++) {
            executor.execute(new Worker(i));
        }

        // We have to shut down the executor
        // We prevent the executor to execute any further tasks
        executor.shutdown();

        // Terminate actual (running) tasks
        try {
            // If we do this, application will be terminated immediately before 100 times execution
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
//                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            throw new RuntimeException(e);
        }
    }
}
