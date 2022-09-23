package de.uniks.vs.simulator.algorithms.example.gcd;

import java.util.Random;

public class GCDUtils {

    static int[] numbers = {30, 108, 12, 24};
    static int count = 0;
    static Random rnd = new Random();

    public static int getRandomNumber(int max) {
        return rnd.nextInt(max);
    }

    public static int getNextNumber() { return numbers[count++]; }

    public static int mods(int a, int b) {
        return ( (a-1) % b) + 1;
    }
}
