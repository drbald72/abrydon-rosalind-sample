package org.abrydon.sandbox.javabio.javafiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.google.common.base.Charsets;

public class FastaFileIteratorTest {

    @Test
    public void testEmptyInput() throws IOException {
        String inputString = "";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8))) {
            try (FastaFileIterator iterator = new FastaFileIterator(inputStream)) {
                assertFalse("Do not expect to get any reads from an empty input stream.", iterator.hasNext());
            }
        }
    }

    @Test
    public void testReadHappyPath() throws IOException {
        String inputString = ">Rosalind_6404\n" +
                "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCC\n" +
                "TCCCACTAATAATTCTGAGG\n" +
                ">Rosalind_5959\n" +
                "CCATCGGTAGCGCATCCTTAGTCCAATTAAGTCCCTATCCAGGCGCTCCGCCGAAGGTCT\n" +
                "ATATCCATTTGTCAGCAGACACGC\n" +
                ">Rosalind_0808\n" +
                "CCACCCTCGTGGTATGGCTAGGCATTCAGGAACCGGAGAACGCTTCAGACCAGCCCGGAC\n" +
                "TGGGAACCTGCGGGCAGTAGGTGGAAT\n";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8))) {
            try (FastaFileIterator iterator = new FastaFileIterator(inputStream)) {
                assertTrue(iterator.hasNext());
                assertEquals(new FastaRead("Rosalind_6404", "",
                        "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCCTCCCACTAATAATTCTGAGG"),
                        iterator.next());

                assertTrue(iterator.hasNext());
                assertEquals(new FastaRead("Rosalind_5959", "",
                        "CCATCGGTAGCGCATCCTTAGTCCAATTAAGTCCCTATCCAGGCGCTCCGCCGAAGGTCTATATCCATTTGTCAGCAGACACGC"),
                        iterator.next());

                assertTrue(iterator.hasNext());
                assertEquals(new FastaRead("Rosalind_0808", "",
                        "CCACCCTCGTGGTATGGCTAGGCATTCAGGAACCGGAGAACGCTTCAGACCAGCCCGGACTGGGAACCTGCGGGCAGTAGGTGGAAT"),
                        iterator.next());

                assertFalse(iterator.hasNext());
            }
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testReadNonFastaText() throws IOException {
        String inputString = "This is not the file you're looking for ....";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8))) {
            try (FastaFileIterator iterator = new FastaFileIterator(inputStream)) {
                assertTrue("Because we implement an iterator, we think we have a next one.", iterator.hasNext());
                iterator.next();
            }
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testReadMissingDataLine() throws IOException {
        String inputString = ">Rosalind_6404\n";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8))) {
            try (FastaFileIterator iterator = new FastaFileIterator(inputStream)) {
                assertTrue("Because we implement an iterator, we think we have a next one.", iterator.hasNext());
                iterator.next();
            }
        }
    }

    @Test
    public void testReadWithExtraWhitespace() throws IOException {
        String inputString = ">Rosalind_6404    \n" +
                "    CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCC    \n" +
                "    TCCCACTAATAATTCTGAGG    \n";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8))) {
            try (FastaFileIterator iterator = new FastaFileIterator(inputStream)) {
                assertTrue(iterator.hasNext());
                assertEquals(new FastaRead("Rosalind_6404", "",
                        "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCCTCCCACTAATAATTCTGAGG"),
                        iterator.next());

                assertFalse(iterator.hasNext());
            }
        }
    }

    @Test
    public void testReadWithComment() throws IOException {
        String inputString = ">Rosalind_6404 additional multi word comment   \n" +
                "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCC\n" +
                "TCCCACTAATAATTCTGAGG\n";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(Charsets.UTF_8))) {
            try (FastaFileIterator iterator = new FastaFileIterator(inputStream)) {
                assertTrue(iterator.hasNext());
                assertEquals(new FastaRead("Rosalind_6404", "additional multi word comment",
                        "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCCTCCCACTAATAATTCTGAGG"),
                        iterator.next());

                assertFalse(iterator.hasNext());
            }
        }
    }

}
