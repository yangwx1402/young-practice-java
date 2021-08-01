package com.young.practice.java18.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yangyong
 */
public class ObjectConverts {

    public static <P, R> R convert(P p, Function<P, R> mapFunction) {
        if (p == null) {
            return null;
        }
        return Optional.ofNullable(mapFunction).map(function -> function.apply(p)).orElseThrow(() -> new RuntimeException("mapFunction is not null!"));
    }

    public static <P, R> List<R> convert(List<P> p, Function<P, R> mapFunction) {
        return Optional.ofNullable(p).orElseGet(ArrayList::new).stream().map(param -> ObjectConverts.convert(param, mapFunction)).collect(Collectors.toList());
    }
}
