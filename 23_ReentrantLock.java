import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ReentrantLockExample {
    private static int counter = 0;
    // default fairness = true
    // then the longest waiting thread will get the lock
    // should be used with try-catch-finally block
    private static Lock lock = new ReentrantLock();

    private static void increment() {
        lock.lock();

        try {
            for(int i = 0; i < 10000; i++) {
                counter++;
            }
        } finally {
//            lock.unlock();
            unlock();
        }
    }

    public static void unlock() {
        lock.unlock();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Counter = " + counter);
    }
}
