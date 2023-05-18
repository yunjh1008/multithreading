import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    private int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task with id " + id + " is in work - thread id: " + Thread.currentThread().getName());
        long duration = (long) (Math.random() * 5);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SingleThreadExecutor {
    public static void main(String[] args) {
        // It is a single thread that will execute the tasks sequentially so one after another
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for(int i = 0; i < 5; i++) {
            executor.execute(new Task(i));
        }

        // We have to shut down the executor
        // We prevent the executor to execute any further tasks
        executor.shutdown();

        // Terminate actual (running) tasks
        try {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
//                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
