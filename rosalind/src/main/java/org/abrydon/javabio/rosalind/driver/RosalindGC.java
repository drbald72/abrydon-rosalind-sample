package org.abrydon.javabio.rosalind.driver;

import java.io.IOException;
import java.io.InputStream;

import org.abrydon.sandbox.javabio.javafiles.FastaFileIterator;
import org.abrydon.sandbox.javabio.javafiles.FastaFileStatistics;
import org.abrydon.sandbox.javabio.javafiles.FastaRead;
import org.abrydon.sandbox.javabio.javafiles.FastaStatisticsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Resources;

public class RosalindGC {
    private final static Logger logger = LoggerFactory.getLogger(RosalindGC.class);

    private static final String FILE_RESOURCE_NAME = "rosalind_gc.txt";

    public static void main(String[] args) {
        try (InputStream inputStream = Resources.getResource(FILE_RESOURCE_NAME).openStream()) {

            FastaStatisticsBuilder statisticsBuilder = new FastaStatisticsBuilder();
            try (FastaFileIterator readIterator = new FastaFileIterator(inputStream)) {
                while (readIterator.hasNext()) {
                    statisticsBuilder.add(readIterator.next());
                }
            }
            FastaFileStatistics statistics = statisticsBuilder.build();
            FastaRead maximumGCRead = statistics.getMaximumGCRead();

            logger.info(maximumGCRead.getLabel());
            logger.info(String.format("%.6f", 100 * maximumGCRead.getGCCount() / (double) maximumGCRead.getLength()));

        } catch (IllegalArgumentException e) {
            logger.error("Unexpected data in input file resource \"{}\".", FILE_RESOURCE_NAME, e);
        } catch (IOException e) {
            logger.error("Failed to load the file resource \"{}\".", FILE_RESOURCE_NAME, e);
        }

    }
}
