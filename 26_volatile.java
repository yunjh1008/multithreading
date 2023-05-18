class Worker implements Runnable {

    // https://nesoy.github.io/articles/2018-06/Java-volatile
    // It will be stored in the main memory
    // 1) variables can be stored on the main memory without the volatile keyword explicitly
    // 2) both of the threads use the same cache
    private volatile boolean isTerminated;

    @Override
    public void run() {
        while(!isTerminated) {
            System.out.println("Working class is running...");
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean isTerminated) {
        this.isTerminated = isTerminated;
    }
}

class Volatile {
    public static void main(String[] args) {
        Worker worker = new Worker();
        Thread t1 = new Thread(worker);
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Thread.stop() is deprecated!
        worker.setTerminated(true);
        System.out.println("Algorithm is terminated");
    }
}
