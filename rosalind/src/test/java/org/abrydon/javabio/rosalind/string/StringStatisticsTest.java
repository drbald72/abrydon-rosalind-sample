package org.abrydon.javabio.rosalind.string;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.abrydon.javabio.rosalind.string.StringStatistics;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class StringStatisticsTest {

    @Test
    public void testCountOccurrenceEmpty() {
        Map<Character, Long> answer = StringStatistics.countOccurrence("");
        assertEquals(Collections.emptyMap(), answer);
    }

    @Test
    public void testCountOccurrenceSingleRepeatedCharacter() {
        Map<Character, Long> answer = StringStatistics.countOccurrence("GGGGGG");
        assertEquals(ImmutableMap.of('G', 6L), answer);
    }

    @Test
    public void testCountOccurrenceHappyPath() {
        Map<Character, Long> answer = StringStatistics
                .countOccurrence("AGCTTTTCATTCTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATAGCAGC");
        assertEquals(ImmutableMap.of('A', 20L, 'C', 12L, 'G', 17L, 'T', 21L), answer);
    }

}
