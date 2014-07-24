package org.abrydon.sandbox.javabio.javafiles;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * Accumulated statistics of a {@link FastaFileIterator}.
 *
 */
@Immutable
public class FastaFileStatistics {

    private final double averageGC;
    private final double averageReadLength;
    private final FastaRead minimumLengthRead;
    private final FastaRead maximumLengthRead;
    private final FastaRead minimumGCRead;
    private final FastaRead maximumGCRead;

    public FastaFileStatistics(double averageGC, double averageReadLength,
            FastaRead minimumLengthRead, FastaRead maximumLengthRead,
            FastaRead minimumGCRead, FastaRead maximumGCRead) {
        Preconditions.checkNotNull(minimumLengthRead);
        Preconditions.checkNotNull(maximumLengthRead);
        Preconditions.checkNotNull(minimumGCRead);
        Preconditions.checkNotNull(maximumGCRead);

        this.averageGC = averageGC;
        this.averageReadLength = averageReadLength;
        this.minimumLengthRead = minimumLengthRead;
        this.maximumLengthRead = maximumLengthRead;
        this.minimumGCRead = minimumGCRead;
        this.maximumGCRead = maximumGCRead;
    }

    /**
     * @return the average GC content (ratio of GC reads to total reads) for the collection of reads.
     */
    public double getAverageGC() {
        return averageGC;
    }

    /**
     * @return the average read length for the collection of reads.
     */
    public double getAverageReadLength() {
        return averageReadLength;
    }

    /**
     * @return the first read with the minimum read length.
     */
    public FastaRead getMinimumLengthRead() {
        return minimumLengthRead;
    }

    /**
     * @return the first read with the maximum read length.
     */
    public FastaRead getMaximumLengthRead() {
        return maximumLengthRead;
    }

    /**
     * @return the first read with the minimum GC content.
     */
    public FastaRead getMinimumGCRead() {
        return minimumGCRead;
    }

    /**
     * @return the first read with the maximum GC content.
     */
    public FastaRead getMaximumGCRead() {
        return maximumGCRead;
    }
}
