class Runner1 extends Thread {
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Runner1 " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Runner2 extends Thread {
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Runner2 " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ThreadClass {
    public static void main(String[] args) {
        Thread t1 = new Runner1();
        Thread t2 = new Runner2();

        t1.start();
        t2.start();
    }
}
