package cp;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionExample {

    public static void main(String... args) {
        // 'constructive' function
        // have to construct the tuple (x,y) for every call to function
        BiFunction<Integer, Integer, Integer> cf = (x, y) -> x * y;
        Integer xTimesY = cf.apply(3, 2);
        System.out.println(xTimesY); //prints "6"

        // 'destructive' function
        Function<Integer, Function<Integer, Integer>> df = x -> (y -> x * y);

        // which can lend to a creating a partially applied function
        Function<Integer, Integer> multiplyBy2 = df.apply(2);

        // the previous is similar to "currying a closure" in Groovy
        // e.g.
        // def df = { Integer x, Integer y -> x * y}
        // def multiplyBy2 = df.curry(2)

        // now we can finish applying the partially applied function
        xTimesY = multiplyBy2.apply(3);
        System.out.println(xTimesY); //prints "6"
    }
}
