package br.com.alura.adopet.api.util;

import java.util.Random;

public class TestUtils {

    private static final Random RANDOM = new Random();

    public static long randomId() {
        return RANDOM.nextLong(9999);
    }

}
