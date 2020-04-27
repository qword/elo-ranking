package qword.readers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qword.domain.EloException;
import qword.domain.dto.Match;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class MatchReaderTest {

    private MatchReader underTest;

    private static InputStream createInputStream(final String input) {
        return new ByteArrayInputStream(input.getBytes(UTF_8));
    }

    @BeforeEach
    void setUp() {
        underTest = new MatchReader();
    }

    @Test
    @DisplayName("Should create an empty collection")
    void shouldCreateEmptyCollection() throws IOException {
        var matches = underTest.read(createInputStream(""));

        assertTrue(matches.isEmpty(), "Should return an empty collection");
    }

    @Test
    @DisplayName("Should create a collection with a single entry")
    void shouldCreateCollectionWithSingleEntry() throws IOException {
        var matches = underTest.read(createInputStream("1 2"));

        assertFalse(matches.isEmpty(), "Should return a non-empty collection");
        final Match match = matches.iterator().next();

        assertEquals(1, match.getWinningPlayer(), "Should return winning player");
        assertEquals(2, match.getLosingPlayer(), "Should return losing player");
    }

    @Test
    @DisplayName("Should create a collection with a single entry, input is tab separated")
    void shouldCreateCollectionWithSingleEntryTabSeparators() throws IOException {
        var matches = underTest.read(createInputStream("45\t56"));

        assertFalse(matches.isEmpty(), "Should return a non-empty collection");
        final Match match = matches.iterator().next();

        assertEquals(45, match.getWinningPlayer(), "Should return winning player");
        assertEquals(56, match.getLosingPlayer(), "Should return losing player");
    }

    @Test
    @DisplayName("Should create a collection from a file")
    void shouldCreateCollectionFromFile() throws IOException {
        var is = getClass().getClassLoader().getResourceAsStream("matches");
        var matches = underTest.read(is);

        assertFalse(matches.isEmpty(), "Should return a non-empty collection");

        final Match firstMatch = matches.iterator().next();
        assertEquals(37, firstMatch.getWinningPlayer(), "Should return winning player");
        assertEquals(38, firstMatch.getLosingPlayer(), "Should return losing player");

        final Match lastMatch = matches.stream().reduce((prev, next) -> next).orElse(null);
        assertEquals(1, lastMatch.getWinningPlayer(), "Should return winning player");
        assertEquals(2, lastMatch.getLosingPlayer(), "Should return losing player");

        assertEquals(4, matches.size(), "Should return the correct number of entries in the collection");
    }

    @Test
    @DisplayName("Should throw an IOException when the InputStream is null")
    void shouldThrowIOExceptionWhenInputStreamIsNull() {
        assertThrows(IOException.class, () -> {
            var is = getClass().getClassLoader().getResourceAsStream("matches-not-existing");
            underTest.read(is);
        });
    }

    @Test
    @DisplayName("Should throw an EloException when a token in the input has less than 2 elements")
    void shouldThrowEloExceptionWhenInputIsMalformed() {
        final EloException exception = assertThrows(EloException.class, () -> underTest.read(createInputStream("6")));

        assertEquals("Error parsing input: given fewer than 2 expected tokens", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw an EloException when a token in the input contains a non-parsable integer")
    void shouldThrowEloExceptionWhenInputIsNotParsable() {
        final EloException exception = assertThrows(EloException.class, () -> underTest.read(createInputStream("6 k")));

        assertEquals("Error parsing numerical input: expected couple of integers, actual values: 6, k", exception.getMessage());
    }

}