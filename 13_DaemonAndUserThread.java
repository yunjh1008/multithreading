class DaemonWorker implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("Daemon thread is running");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class UserWorker implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User thread finishes execution");
    }
}

class DaemonAndUserThread {
    public static void main(String[] args) {
        Thread t1 = new Thread(new DaemonWorker());
        Thread t2 = new Thread(new UserWorker());

        t1.setDaemon(true);

        t1.start();
        t2.start();
    }
}
