package org.abrydon.javabio.rosalind.string;

import static org.junit.Assert.assertEquals;

import org.abrydon.javabio.rosalind.string.ProfileMatrix.MatrixColumn;
import org.junit.Test;

public class ProfileMatrixTest {

    private static final String NL = System.lineSeparator();

    @Test
    public void testMatrixColumnEmpty() {
        MatrixColumn column = new MatrixColumn();
        assertEquals(0, column.get('G'));
        assertEquals(0, column.get('A'));
        assertEquals(0, column.get('T'));
        assertEquals(0, column.get('C'));
    }

    @Test
    public void testMatrixColumnAdd() {
        MatrixColumn column = new MatrixColumn().add('A').add('A').add('T');
        assertEquals(0, column.get('G'));
        assertEquals(2, column.get('A'));
        assertEquals(1, column.get('T'));
        assertEquals(0, column.get('C'));
    }

    @Test
    public void testEmptyInput() {
        ProfileMatrix testObject = new ProfileMatrix();
        assertEquals("", testObject.concensus());
        assertEquals("A:" + NL +
                "C:" + NL +
                "G:" + NL +
                "T:" + NL, testObject.toString());
    }

    @Test
    public void testSingleEntry() {
        ProfileMatrix testObject = new ProfileMatrix();
        testObject.add("GACT");
        assertEquals("GACT", testObject.concensus());
        assertEquals("A: 0 1 0 0" + NL +
                "C: 0 0 1 0" + NL +
                "G: 1 0 0 0" + NL +
                "T: 0 0 0 1" + NL, testObject.toString());
    }

    @Test
    public void testHappyPath() {
        ProfileMatrix testObject = new ProfileMatrix();
        testObject.add("ATCCAGCT");
        testObject.add("GGGCAACT");
        testObject.add("ATGGATCT");
        testObject.add("AAGCAACC");
        testObject.add("TTGGAACT");
        testObject.add("ATGCCATT");
        testObject.add("ATGGCACT");
        assertEquals("ATGCAACT", testObject.concensus());
        assertEquals("A: 5 1 0 0 5 5 0 0" + NL +
                "C: 0 0 1 4 2 0 6 1" + NL +
                "G: 1 1 6 3 0 1 0 0" + NL +
                "T: 1 5 0 0 0 1 1 6" + NL, testObject.toString());
    }

}
