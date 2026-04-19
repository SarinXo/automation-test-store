package util;

import com.github.javafaker.Faker;

import java.util.Random;

public class RandomUtils {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static int number(int max) {
        return random.nextInt(max) + 1;
    }

}
