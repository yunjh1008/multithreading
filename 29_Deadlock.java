import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Deadlock {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {

        Deadlock deadlock = new Deadlock();

        // after Java 8 it's possible to create threads like this
        new Thread(deadlock::worker1, "worker1").start();
        new Thread(deadlock::worker2, "worker2").start();
    }

    // Application is not terminated with cyclic dependency
    // blocked state - threads are waiting for the other thread to finish execution
    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 acquires the lock1...");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("Worker1 acquires the lock2...");

        lock1.unlock();
        lock2.unlock();
    }

    // cyclic dependency if worker1(lock1, lock2), worker2(lock2, lock1)
    public void worker2() {
        lock2.lock();
        System.out.println("Worker2 acquires the lock2...");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock1.lock();
        System.out.println("Worker2 acquires the lock1...");

        lock1.unlock();
        lock2.unlock();
    }
}
