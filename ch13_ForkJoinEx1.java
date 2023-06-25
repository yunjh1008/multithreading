import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumTask extends RecursiveTask<Long> {
    private Long from;
    private Long to;

    public SumTask(Long from, Long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Long compute() {
        Long size = to - from + 1;

        if (size <= 5) return sum();

        Long half = (from + to) / 2;

        // 범위를 반으로 나눠서 두 개의 작업을 생성
        SumTask leftSum = new SumTask(from, half);
        SumTask rightSum = new SumTask(half + 1, to);

        // fork()를 호출하면 결과를 기다리지 않고 다음 문장인 return문으로 넘어간다.
        // return문에서 compute()가 재귀호출될 때, join()은 호출되지 않는다.
        // 작업을 더이상 나눌 수 없게 되었을 때, compute()의 재귀 호출은 끝나고 join()의 결과를 기다렸다가 더해서 결과를 반환한다.
        // 재귀호출된 compute()가 모두 종료될 때, 최종 결과를 얻는다.
        leftSum.fork();

        return rightSum.compute() + leftSum.join();
    }

    public Long sum() {
        Long result = 0L;

        for(long i = from; i <= to; i++) {
            result += i;
        }

        return result;
    }
}

class ForkJoinEx1 {
    static final ForkJoinPool pool = new ForkJoinPool();

    public static void main(String[] args) {
        Long from = 1L;
        Long to = 100_000_000L;

        SumTask task = new SumTask(from, to);

        Long start = System.currentTimeMillis();
        Long result = pool.invoke(task);
        Long end = System.currentTimeMillis();
        System.out.println("Elapsed time (1 core): " + (end - start)); // 658 ms
        System.out.printf("Sum of %d to %d = %d\n", from, to, result);
        System.out.println();

        result = 0L;
        start = System.currentTimeMillis();
        for(long i = from; i <= to; i++) {
            result += i;
        }
        end = System.currentTimeMillis();
        System.out.println("Elapsed time (4 cores): " + (end - start)); // 142 ms
        System.out.printf("Sum of %d to %d = %d\n", from, to, result);
    }
}
