import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class StockMarketUpdater implements Runnable {

    @Override
    public void run() {
        System.out.println("Updating and downloading stock related data from the web...");
    }
}

class ScheduledThreadPool {
    public static void main(String[] args) {
        // It is a single thread that will execute the tasks sequentially
        // so one after another
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        // 2nd param: initial delay 1 second
        // 3rd param: call the method every 2 seconds (frequency)
        executor.scheduleAtFixedRate(new StockMarketUpdater(), 1000, 2000, TimeUnit.MILLISECONDS);
    }
}
