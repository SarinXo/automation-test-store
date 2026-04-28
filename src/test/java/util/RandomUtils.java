package util;

import java.util.Random;

public class RandomUtils {

    private static final Random random = new Random();

    public static int number(int max) {
        return random.nextInt(max) + 1;
    }

}
