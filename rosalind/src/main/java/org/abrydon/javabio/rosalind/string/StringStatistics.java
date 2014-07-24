package org.abrydon.javabio.rosalind.string;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringStatistics {

    /**
     * @param string to be counted.
     * @return map of character to occurrence count for each character in string.
     */
    public static Map<Character, Long> countOccurrence(String string) {
        return string.chars()
                .mapToObj(a -> (char) a)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
