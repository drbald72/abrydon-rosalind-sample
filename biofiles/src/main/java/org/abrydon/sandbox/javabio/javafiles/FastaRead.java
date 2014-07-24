package org.abrydon.sandbox.javabio.javafiles;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

/**
 * A single DNA read, consisting of the characters A, C, G and T with a label and comment string.
 *
 */
@Immutable
public class FastaRead {

    private static final String DNA_STRING_PATTERN = "[ACGT]+";
    private static final Map<Character, Character> REVERSE_COMPLEMENT_MAP =
            ImmutableMap.of('A', 'T', 'T', 'A', 'G', 'C', 'C', 'G');

    private final String label;
    private final String comment;
    private final String dnaString;

    public FastaRead(String label, String comment, String dnaString) {
        Preconditions.checkArgument(dnaString.matches(DNA_STRING_PATTERN), "Valid DNA pattern should contain only A,C,G and T");

        this.label = label;
        this.comment = comment;
        this.dnaString = dnaString;
    }

    /**
     * @return read label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return read comment, which will often be empty
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return the read length
     */
    public int getLength() {
        return dnaString.length();
    }

    /**
     * @return the read string, consisting of characters A,C,G,T. It may not be empty.
     */
    public String getDNAString() {
        return dnaString;
    }

    /**
     * @return transcribed RNA string, with 'T' replaced by 'U'.
     */
    public String getRNAString() {
        return dnaString.replaceAll("T", "U");
    }

    /**
     * @return the reverse complement of the read, with A <-> T, C <-> G, and the string reversed.
     */
    public String getReverseComplement() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : dnaString.toCharArray()) {
            stringBuilder.insert(0, REVERSE_COMPLEMENT_MAP.get(c));
        }
        return stringBuilder.toString();
    }

    /**
     * @return the number of G or C characters in the string.
     */
    public long getGCCount() {
        return dnaString.chars()
                .filter(a -> (a == 'G' || a == 'C'))
                .boxed()
                .collect(Collectors.counting());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(">");
        stringBuilder.append(label);
        if (!comment.isEmpty()) {
            stringBuilder.append(" ");
            stringBuilder.append(comment);
        }
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(dnaString);
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FastaRead other = (FastaRead) obj;
        return Objects.equals(label, other.label) &&
                Objects.equals(comment, other.comment) &&
                Objects.equals(dnaString, other.dnaString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, comment, dnaString);
    }
}
