package qword.readers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qword.domain.EloException;
import qword.domain.dto.Name;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class NameReaderTest {

    private NameReader underTest;

    private static InputStream createInputStream(final String input) {
        return new ByteArrayInputStream(input.getBytes(UTF_8));
    }

    @BeforeEach
    void setUp() {
        underTest = new NameReader();
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
        var matches = underTest.read(createInputStream("7 Mary"));

        assertFalse(matches.isEmpty(), "Should return a non-empty collection");
        final Name name = matches.iterator().next();

        assertEquals(7, name.getId(), "Should return id");
        assertEquals("Mary", name.getValue(), "Should return value for name");
    }

    @Test
    @DisplayName("Should create a collection with a single entry, input is tab separated")
    void shouldCreateCollectionWithSingleEntryTabSeparators() throws IOException {
        var matches = underTest.read(createInputStream("46\tValentino"));

        assertFalse(matches.isEmpty(), "Should return a non-empty collection");
        final Name name = matches.iterator().next();

        assertEquals(46, name.getId(), "Should return id");
        assertEquals("Valentino", name.getValue(), "Should return value for name");
    }

    @Test
    @DisplayName("Should create a collection from a file")
    void shouldCreateCollectionFromFile() throws IOException {
        var is = getClass().getClassLoader().getResourceAsStream("names");
        var matches = underTest.read(is);

        assertFalse(matches.isEmpty(), "Should return a non-empty collection");

        final Name firstName = matches.iterator().next();
        assertEquals(4, firstName.getId(), "Should return id");
        assertEquals("Bunny", firstName.getValue(), "Should return value for name");

        final Name lastName = matches.stream().reduce((prev, next) -> next).orElse(null);
        assertEquals(6, lastName.getId(), "Should return id");
        assertEquals("Kami", lastName.getValue(), "Should return value for name");

        assertEquals(3, matches.size(), "Should return the correct number of entries in the collection");
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
        final EloException exception = assertThrows(EloException.class, () -> underTest.read(createInputStream("7")));

        assertEquals("Error parsing input: given fewer than 2 expected tokens", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw an EloException when a token in the input contains a non-parsable integer")
    void shouldThrowEloExceptionWhenInputIsNotParsable() {
        final EloException exception = assertThrows(EloException.class, () -> underTest.read(createInputStream("tt Noemi")));

        assertEquals("Error parsing id: expected integer, actual value: tt", exception.getMessage());
    }

}