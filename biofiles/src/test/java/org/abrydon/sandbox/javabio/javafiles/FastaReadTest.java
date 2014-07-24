package org.abrydon.sandbox.javabio.javafiles;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FastaReadTest {

    private static final String NL = System.lineSeparator();

    @Test(expected = IllegalArgumentException.class)
    public void testFastaReadDisallowsEmptyReads() {
        new FastaRead("label", "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFastaReadDisallowsInvalidCharacters() {
        new FastaRead("label", "", "GATCX");
    }

    @Test
    public void testGetLengthHappyPath() {
        String input = "GATGGAACTTGACTACGTAAATT";
        assertEquals(input.length(), new FastaRead("label", "", input).getLength());
    }

    @Test
    public void testGetRNAStringHappyPath() {
        assertEquals("GAUGGAACUUGACUACGUAAAUU", new FastaRead("label", "", "GATGGAACTTGACTACGTAAATT").getRNAString());
    }

    @Test
    public void testGetReverseComplementStringHappyPath() {
        assertEquals("GAUGGAACUUGACUACGUAAAUU", new FastaRead("label", "", "GATGGAACTTGACTACGTAAATT").getRNAString());
    }

    @Test
    public void testReverseComplement() {
        assertEquals("AAAACCCGGT", new FastaRead("label", "", "ACCGGGTTTT").getReverseComplement());
    }

    @Test
    public void testGCCount() {
        assertEquals(3, new FastaRead("label", "", "GCTATAAATC").getGCCount());
    }

    @Test
    public void testToStringWithoutComment() {
        String label = "label";
        String dnaString = "GATGGAACTTGACTACGTAAATT";
        FastaRead read = new FastaRead(label, "", dnaString);
        assertEquals(">label" + NL + dnaString + NL, read.toString());
    }

    @Test
    public void testToStringWithoComment() {
        String label = "label";
        String comment = "a comment";
        String dnaString = "GATGGAACTTGACTACGTAAATT";
        FastaRead read = new FastaRead(label, comment, dnaString);
        assertEquals(">label a comment" + NL + dnaString + NL, read.toString());
    }
}
