package org.abrydon.javabio.rosalind.driver;

import java.io.IOException;
import java.util.Map;

import org.abrydon.javabio.rosalind.string.StringStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class RosalindDNA {
    private final static Logger logger = LoggerFactory.getLogger(RosalindDNA.class);

    private static final String FILE_RESOURCE_NAME = "rosalind_dna.txt";

    public static void main(String[] args) {
        try {
            String input = Resources.toString(Resources.getResource(FILE_RESOURCE_NAME), Charsets.UTF_8).trim();
            Map<Character, Long> counts = StringStatistics.countOccurrence(input);

            logger.debug("mapped counts for string: {}", counts);

            logger.info(String.format("%d %d %d %d",
                    counts.get('A'), counts.get('C'), counts.get('G'), counts.get('T')));

        } catch (IOException e) {
            logger.error("Failed to load the file resource {}", FILE_RESOURCE_NAME);
        }

    }
}
