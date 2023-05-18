import java.util.concurrent.atomic.AtomicInteger;

class AtomicVariables {

    private static AtomicInteger counter = new AtomicInteger(0);

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

        System.out.println("Counter: " + counter);
    }

    // no need to use synchronized keyword with AtomicInteger
    public static void increment() {
        for(int i = 0; i < 10000; i++) {
            counter.getAndIncrement();
        }
    }
}
