package org.abrydon.sandbox.javabio.javafiles;

public class FastaStatisticsBuilder {

    private double sumReads;
    private double sumLength;
    private double sumGC;
    private int minimumLength;
    private FastaRead minimumLengthRead;
    private int maximumLength;
    private FastaRead maximumLengthRead;
    private double minimumGC;
    private FastaRead minimumGCRead;
    private double maximumGC;
    private FastaRead maximumGCRead;

    public FastaStatisticsBuilder() {
        this.sumReads = 0.0;
        this.sumLength = 0.0;
        this.sumGC = 0.0;
        this.minimumLength = Integer.MAX_VALUE;
        this.maximumLength = Integer.MIN_VALUE;
        this.minimumGC = Double.MAX_VALUE;
        this.maximumGC = Double.MIN_VALUE;
    }

    /**
     * @param read to be added to statistics.
     */
    public void add(FastaRead read) {
        final int length = read.getLength();
        final long gcCount = read.getGCCount();

        sumReads += 1;
        sumLength += length;
        sumGC += gcCount;

        if (length < minimumLength) {
            minimumLength = length;
            minimumLengthRead = read;
        }

        if (length > maximumLength) {
            maximumLength = length;
            maximumLengthRead = read;
        }

        double gc = gcCount / (double) length;
        if (gc < minimumGC) {
            minimumGC = gc;
            minimumGCRead = read;
        }

        if (gc > maximumGC) {
            maximumGC = gc;
            maximumGCRead = read;
        }
    }

    /**
     * @return the accumulated statistics of added reads.
     * @throws IllegalArgumentException if not reads have been added.
     */
    public FastaFileStatistics build() {
        if (sumReads < 1) {
            throw new IllegalArgumentException("Attempt to access fasta file statistics without adding any reads.");
        }
        return new FastaFileStatistics(sumGC / sumLength, sumLength / sumReads,
                minimumLengthRead, maximumLengthRead,
                minimumGCRead, maximumGCRead);
    }
}
