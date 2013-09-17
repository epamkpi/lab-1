package ua.kpi.montecarlo;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Calculates how many of {@code totalPoints} randomly generated points in an elementary square fits inside of a quarter
 * of the circle encircled into it.
 */
public class PointCounter implements Callable<Long> {
    private static Random random = new Random();
    private long totalPoints;

    public static void main(String[] args) throws Exception {
        PointCounter counter = new PointCounter(250000000);
        System.out.println(counter.call());
        System.out.println(Executors.newSingleThreadExecutor().submit(counter).get());
    }
    /**
     * Creates {@code PointCounter} that uses {@code totalPoints} points in computations.
     * @param totalPoints number of randomly generated points
     */
    public PointCounter(long totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * Counts number of points in a quarter of the circle.
     * @return number of points fit in the circle
     * @throws Exception not thrown
     */
    @Override
    public Long call() throws Exception {
        long result = 0;
        for (long i = 0; i < totalPoints; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (Math.sqrt(x * x + y * y) < 1.0) {
                result++;
            }
        }
        return result;
    }
}
