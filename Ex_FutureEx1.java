import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Add implements Callable<Integer> {
    private int a;
    private int b;

    public Add(int a, int b) {
        this.a = a;
        this.b = b;
    }


    @Override
    public Integer call() throws Exception {
        return a + b;
    }
}

class Multiply10 implements Callable<Integer> {
    private int target;

    public Multiply10(int target) {
        this.target = target;
    }

    @Override
    public Integer call() throws Exception {
        return target * 10;
    }
}

class FutureEx1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<Integer>> finalFutureResultList = new ArrayList<>();
        List<Integer> finalResultList = new ArrayList<>();

        Long start = System.currentTimeMillis();

        for(int i = 0; i < 1000000; i++) {
            Future<Integer> intermediateFutureResult = executorService.submit(new Add(i, i));
            Integer intermediateResult = intermediateFutureResult.get();
            Future<Integer> finalFutureResult = executorService.submit(new Multiply10(intermediateResult));
            finalFutureResultList.add(finalFutureResult);
        }

        for(Future<Integer> future : finalFutureResultList) {
            finalResultList.add(future.get());
        }

        Long end = System.currentTimeMillis();
        System.out.println("Elapsed time (ms) = " + (end - start)); // 5086 ms

//        System.out.println(finalResultList);
        executorService.shutdown();
    }
}
