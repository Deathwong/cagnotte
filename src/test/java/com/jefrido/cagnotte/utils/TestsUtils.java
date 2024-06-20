package com.jefrido.cagnotte.utils;

import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;

@UtilityClass
public class TestsUtils {

    private static EasyRandom getRandomGenerator() {
        return new EasyRandom();
    }

    public <T> T getRandomTestObject(Class<T> clazz) {
        return getRandomGenerator().nextObject(clazz);
    }
}
