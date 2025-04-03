package com.example;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LambdaExample {
    public static void main(String[] args) {
        List<String> fruits = List.of("Apple", "Banana", "Orange");

        // ラムダ式
        fruits.forEach(fruit -> System.out.println(fruit));

        // メソッド参照（ラムダ式の省略形）
        fruits.forEach(System.out::println);

        //（引数） -> {処理}
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<Integer, Integer, Integer> add2 = (a, b) -> {
            return a + b;
        };

        Function<String, String> toUpper = str -> str.toUpperCase();
        System.out.println(toUpper.apply("大文字変換"+"abc"));
    }
}
