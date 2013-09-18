package ua.kpi.montecarlo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Calculates Pi number value using Monte Carlo method.
 */
public class PiMonteCarlo {
    // Default number of threads used in computations if no thread number specified.
    private static final int DEFAULT_THREAD_NUMBER = 4;
    // Total number of points used in Pi computations
    private static final int POINT_NUMBER = 1_000_000;//_000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int threadNumber = DEFAULT_THREAD_NUMBER;
        if (args.length != 0) {
            threadNumber = Integer.valueOf(args[0]);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        List<Future<Long>> results = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            results.add(executorService.submit(new PointCounter(POINT_NUMBER / threadNumber)));
        }
        long insidePoints = 0;
        for (Future<Long> future : results) {
            insidePoints += future.get();
        }
        double pi = (insidePoints / (double) POINT_NUMBER) * 4;
        double timing = System.currentTimeMillis() - startTime;
        System.out.println("PI is " + pi);
        System.out.println("THREADS " + threadNumber);
        System.out.println("ITERATIONS " + POINT_NUMBER);
        System.out.println("TIME " + timing + "ms");
        executorService.shutdown();
    }
}
