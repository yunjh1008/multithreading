class SynchronizationProblem {
    public static int counter1 = 0;
    public static int counter2 = 0;

    // because app object has a single lock
    // this is why the methods cannot be executed "at the same time" - time slicing algorithm
    // usually it's not a good practice to use synchronized keyword
    public static synchronized void increment1() {
        counter1++;
    }

    public static void increment1_1() {
        // class level locking
        // rule of thumb: we synchronize blocks that are 100% necessary
        // synchronized(this)
        synchronized (SynchronizationProblem.class ) {
            counter1++;
        }
    }

    public static synchronized void increment2() {
        counter2++;
    }

    public static void increment2_1() {
        // class level locking
        // synchronized(this)
        synchronized (SynchronizationProblem.class ) {
            counter2++;
        }
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++) {
                    increment1();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++) {
                    increment2();
                }
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

        System.out.println("The counter1 is: " + counter1);
        System.out.println("The counter2 is: " + counter2);
    }

    public static void main(String[] args) {
        process();
    }
}
