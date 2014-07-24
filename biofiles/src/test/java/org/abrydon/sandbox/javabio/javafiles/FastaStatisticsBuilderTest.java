package org.abrydon.sandbox.javabio.javafiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.abrydon.sandbox.javabio.javafiles.FastaFileStatistics;
import org.abrydon.sandbox.javabio.javafiles.FastaRead;
import org.abrydon.sandbox.javabio.javafiles.FastaStatisticsBuilder;
import org.junit.Test;

public class FastaStatisticsBuilderTest {

    private static final double TOLERANCE = 1.0e-7;

    private final FastaStatisticsBuilder testObject = new FastaStatisticsBuilder();

    @Test(expected = IllegalArgumentException.class)
    public void testBuildEmptyInput() {
        testObject.build();
    }

    @Test
    public void testBuildSingleRead() {
        String dnaString = "GCTT";
        FastaRead read = new FastaRead("label", "label", dnaString);
        testObject.add(read);
        FastaFileStatistics result = testObject.build();
        assertEquals(result.getAverageGC(), 0.5, TOLERANCE);
        assertEquals(result.getAverageReadLength(), 4.0, TOLERANCE);
        assertSame(result.getMaximumGCRead(), read);
        assertSame(result.getMinimumGCRead(), read);
        assertSame(result.getMaximumLengthRead(), read);
        assertSame(result.getMinimumLengthRead(), read);
    }

    @Test
    public void testBuildHappyPath() {
        FastaRead read1 = new FastaRead("Rosalind_6404", "",
                "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCCTCCCACTAATAATTCTGAGG");
        FastaRead read2 = new FastaRead("Rosalind_5959", "",
                "CCATCGGTAGCGCATCCTTAGTCCAATTAAGTCCCTATCCAGGCGCTCCGCCGAAGGTCTATATCCATTTGTCAGCAGACACGC");
        FastaRead read3 = new FastaRead("Rosalind_0808", "",
                "CCACCCTCGTGGTATGGCTAGGCATTCAGGAACCGGAGAACGCTTCAGACCAGCCCGGACTGGGAACCTGCGGGCAGTAGGTGGAAT");

        testObject.add(read1);
        testObject.add(read2);
        testObject.add(read3);

        FastaFileStatistics result = testObject.build();

        assertSame(read3, result.getMaximumGCRead());
        assertEquals("expect GC ratio to match answer of sample question",
                0.60919540, read3.getGCCount() / (double) read3.getLength(), TOLERANCE);
    }
}
