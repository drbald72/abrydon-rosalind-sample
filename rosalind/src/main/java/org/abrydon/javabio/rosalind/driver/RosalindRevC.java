package org.abrydon.javabio.rosalind.driver;

import java.io.IOException;

import org.abrydon.sandbox.javabio.javafiles.FastaRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class RosalindRevC {
    private final static Logger logger = LoggerFactory.getLogger(RosalindRevC.class);

    private static final String FILE_RESOURCE_NAME = "rosalind_revc.txt";

    public static void main(String[] args) {
        try {
            String dna = Resources.toString(Resources.getResource(FILE_RESOURCE_NAME), Charsets.UTF_8).trim();
            FastaRead read = new FastaRead("", "", dna);

            logger.info(read.getReverseComplement());

        } catch (IllegalArgumentException e) {
            logger.error("Unexpected data in input file resource \"{}\".", FILE_RESOURCE_NAME, e);
        } catch (IOException e) {
            logger.error("Failed to load the file resource \"{}\".", FILE_RESOURCE_NAME, e);
        }

    }
}
