package org.abrydon.javabio.rosalind.driver;

import java.io.IOException;
import java.io.InputStream;

import org.abrydon.javabio.rosalind.string.ProfileMatrix;
import org.abrydon.sandbox.javabio.javafiles.FastaFileIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Resources;

public class RosalindCons {
    private final static Logger logger = LoggerFactory.getLogger(RosalindCons.class);

    private static final String FILE_RESOURCE_NAME = "rosalind_cons.txt";

    public static void main(String[] args) {
        try (InputStream inputStream = Resources.getResource(FILE_RESOURCE_NAME).openStream()) {

            ProfileMatrix profileMatrix = new ProfileMatrix();
            try (FastaFileIterator readIterator = new FastaFileIterator(inputStream)) {
                while (readIterator.hasNext()) {
                    profileMatrix.add(readIterator.next().getDNAString());
                }
            }

            logger.info("\n\n{}\n{}\n", profileMatrix.concensus(), profileMatrix);

        } catch (IllegalArgumentException e) {
            logger.error("Unexpected data in input file resource \"{}\".", FILE_RESOURCE_NAME, e);
        } catch (IOException e) {
            logger.error("Failed to load the file resource \"{}\".", FILE_RESOURCE_NAME, e);
        }

    }
}
