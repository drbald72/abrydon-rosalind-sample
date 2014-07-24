package org.abrydon.sandbox.javabio.javafiles;

import java.io.Closeable;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * A simplified file reader for scanning a simplified subset of the FASTA file format from<br>
 * {@link http://en.wikipedia.org/wiki/FASTA_format}<br>
 * with the following limitations
 * <ul>
 * <li>Only DNA data is supported (ACGT)
 * <li>; comments and * continuations are not supported
 * <li>no line-length limit is imposed.
 * </ul>
 *
 * For toy data it is sufficient to scan the entire FASTA file or resource into memory and split it. However, real data can
 * typically exceed 10s of gigabytes of data, and may require unzipping on the fly.
 */
public class FastaFileIterator implements Iterator<FastaRead>, Closeable {
    private static final Logger logger = LoggerFactory.getLogger(FastaFileIterator.class);

    private final Scanner scanner;

    @SuppressWarnings("resource")
    public FastaFileIterator(InputStream inputStream) {
        this.scanner = new Scanner(inputStream).useDelimiter(">");
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public FastaRead next() {
        String readText = scanner.next();
        List<String> lines =
                Lists.newArrayList(readText.replace("\r", "").split("\n"))
                        .stream()
                        .map(a -> a.trim())
                        .collect(Collectors.toList());

        if (lines.size() > 1) {
            String[] labelComment = lines.get(0).split(" +", 2);
            if (labelComment.length > 0) {
                String label = labelComment[0];
                String comment = labelComment.length > 1 ? labelComment[1] : "";

                lines.remove(0);
                String dnaString = String.join("", lines);

                return new FastaRead(label, comment, dnaString);
            }
        }
        logger.warn("Failed to parse scanned fasta text: \"{}\"", readText);
        throw new NoSuchElementException();
    }

    @Override
    public void close() {
        scanner.close();
    }

}
