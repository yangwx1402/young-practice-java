package com.young.practice.java18.lambda;

import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ObjectConvertsExample {

    @Test
    public void testString2Int() {
        List<String> randomStringList = IntStream.rangeClosed(0, 10).mapToObj(number -> number + "").collect(Collectors.toList());
        List<Integer> intList = ObjectConverts.convert(randomStringList, (Function<String,Integer>) string -> Integer.parseInt(string));
        System.out.println(intList);

    }
}
