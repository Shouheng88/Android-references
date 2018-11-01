package me.shouheng.layout;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void dpTestCase1() {
        int width = 720, height = 1280;
        float inch = 4.7f;
        float result = (float) (Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / inch);
        assertEquals(360, result, 0.1);
    }

    @Test
    public void dpTestCase2() {
        int width = 1920, height = 1080;
        float inch = 5f;
        float result = (float) (Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / inch);
        assertEquals(440, result, 0.1);
    }
}