package org.abrydon.javabio.rosalind.string;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ProfileMatrix {

    private static final MatrixColumn EMPTY_COLUMN = new MatrixColumn();
    private static final Character[] TABLE_ORDERED_CHARACTERS = new Character[] { 'A', 'C', 'G', 'T' };

    private final List<MatrixColumn> columns;

    public ProfileMatrix() {
        this.columns = Lists.newArrayList();
    }

    /**
     * @param dnaString to add to profile matrix, which must be same length for each call.
     */
    public void add(String dnaString) {
        final int dnaStringLength = dnaString.length();
        Preconditions.checkArgument(columns.isEmpty() || dnaStringLength == columns.size());

        if (columns.isEmpty()) {
            columns.addAll(Collections.nCopies(dnaStringLength, EMPTY_COLUMN));
        }

        for (int columnIndex = 0; columnIndex < dnaStringLength; columnIndex++) {
            char ch = dnaString.charAt(columnIndex);
            columns.set(columnIndex, columns.get(columnIndex).add(ch));
        }
    }

    /**
     * @return consensus string, which is the string containing most characters in each position.
     */
    public String concensus() {
        return columns.stream()
                .map(c -> c.concensus())
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char ch : TABLE_ORDERED_CHARACTERS) {
            sb.append(ch);
            sb.append(":");
            for (MatrixColumn column : columns) {
                sb.append(" ");
                sb.append(column.get(ch));
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Immutable
    static class MatrixColumn {
        private final Map<Character, Integer> counts;

        MatrixColumn() {
            this(ImmutableMap.of('A', 0, 'C', 0, 'G', 0, 'T', 0));
        }

        private MatrixColumn(Map<Character, Integer> counts) {
            this.counts = counts;
        }

        MatrixColumn add(char ch) {
            Preconditions.checkArgument("ACGT".indexOf(ch) >= 0);

            Map<Character, Integer> copy = Maps.newHashMap(counts);
            copy.put(ch, copy.get(ch) + 1);
            return new MatrixColumn(ImmutableMap.copyOf(copy));
        }

        public int get(char ch) {
            Preconditions.checkArgument("ACGT".indexOf(ch) >= 0);
            return counts.get(ch);
        }

        public char concensus() {
            char result = 'A';
            int bestCount = 0;
            for (Entry<Character, Integer> count : counts.entrySet()) {
                if (count.getValue() > bestCount) {
                    bestCount = count.getValue();
                    result = count.getKey();
                }
            }
            return result;
        }
    }

}
