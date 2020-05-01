package qword.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("Should retrieve initial rating for a player which played no matches")
    void shouldGetInitialRating() {
        final int initialRating = 56789;
        final Player underTest = new Player(1, initialRating, "Ryu");

        assertTrue(underTest.getAllMatchResults().isEmpty(), "Should have played no matches");
        assertEquals(initialRating, underTest.getRating(), "Should have initial rating");
    }

    @Test
    @DisplayName("Should retrieve rating after last match")
    void shouldGetRatingAfterLastMatch() {
        final int ratingAfterLastMatch = 46313;
        final Player underTest = new Player(1, 333, "Ken");
        underTest.addResult(new MatchResult(null, true, 66));
        underTest.addResult(new MatchResult(null, true, ratingAfterLastMatch));

        assertEquals(2, underTest.getAllMatchResults().size(), "Should have played 2 matches");
        assertEquals(ratingAfterLastMatch, underTest.getRating(), "Should have rating after last match");
    }

    @Test
    @DisplayName("Should correctly count the number of wins")
    void shouldCorrectlyCountNumberOfWins() {
        final Player underTest = new Player(1, 0, "Chun-Li");
        underTest.addResult(new MatchResult(null, true, 0));
        underTest.addResult(new MatchResult(null, true, 0));
        underTest.addResult(new MatchResult(null, false, 0));
        underTest.addResult(new MatchResult(null, true, 0));
        underTest.addResult(new MatchResult(null, false, 0));
        underTest.addResult(new MatchResult(null, true, 0));

        assertEquals(2, underTest.getWinLossesDifference(), "Should have a +2 result");
    }

    @Test
    @DisplayName("Should correctly count the number of losses")
    void shouldCorrectlyCountNumberOfLosses() {
        final Player underTest = new Player(1, 0, "Guile");
        underTest.addResult(new MatchResult(null, true, 0));
        underTest.addResult(new MatchResult(null, false, 0));
        underTest.addResult(new MatchResult(null, false, 0));
        underTest.addResult(new MatchResult(null, true, 0));
        underTest.addResult(new MatchResult(null, false, 0));
        underTest.addResult(new MatchResult(null, false, 0));
        underTest.addResult(new MatchResult(null, false, 0));

        assertEquals(-3, underTest.getWinLossesDifference(), "Should have a -3 result");
    }
}