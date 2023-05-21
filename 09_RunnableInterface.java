class Runner1 implements Runnable {
    /*
    Usually using the Runnable interface approach is preferred.
    if we extend Thread then we can’t extend any other class (usually a huge disadvantage)
    because in Java a given class can extends one class exclusively
    a class may implement more interfaces as well
    so implementing the Runnable interface can do no harm in the software logic (now or in the future)
     */
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println("Runner1 " + i);
        }
    }
}

class Runner2 implements Runnable {
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println("Runner2 " + i);
        }
    }
}

class RunnableInterface {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1());
        Thread t2 = new Thread(new Runner2());

        t1.start();
        t2.start();
    }
}
