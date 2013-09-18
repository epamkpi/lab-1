package ua.kpi.montecarlo;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PointCounterTest {
    private static final long POINT_NUMBER = 1_000_000;
    public static final double ACCURACY = 0.01;

    @Test
    public void count() throws Exception {
        assertEquals(new PointCounter(POINT_NUMBER).call() / (double) POINT_NUMBER, Math.PI / 4, ACCURACY,
                "Pi value approximation failed");
    }
}
