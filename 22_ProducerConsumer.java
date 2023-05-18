import java.util.ArrayList;
import java.util.List;

class Processor {
    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;
    private final Object lock = new Object();
    private int value = 0;

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    value = 0;
                    lock.wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    // we can call the notify because other thread will be notified only when it is in a waiting state
                    // do further operation (while loop)
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting for adding items...");
                    lock.wait();
                } else {
                    System.out.println("Removing: " + list.remove(list.size() - 1));
                    // we can call the notify because other thread will be notified
                    // only when it is in a waiting state
                    // wake up other thread
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }
}

class ProducerConsumer {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}
