class Worker implements Runnable {

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}

class ThreadPriority {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker());

        // main thread with priority 10
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();

        // main thread with priority 5
        System.out.println("This is in the main thread...");
    }
}
