import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CompletableFutureEx1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> finalResultList = new ArrayList<>();

        Long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++) {
            int current = i;
            Integer finalResult = CompletableFuture.supplyAsync(() -> add(current, current), executorService)
                    .thenApplyAsync(result -> multiply10(result), executorService)
                    .get();
            finalResultList.add(finalResult);
        }
        Long end = System.currentTimeMillis();
        System.out.println("Elapsed time (ms) = " + (end - start)); // 2516 ms
//        System.out.println(finalResultList);

        executorService.shutdown();
    }

    public static Integer add(int a, int b) {
        return a + b;
    }

    public static Integer multiply10(int target) {
        return target * 10;
    }
}
