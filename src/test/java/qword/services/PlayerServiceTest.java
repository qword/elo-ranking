package qword.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import qword.domain.MatchResult;
import qword.domain.Player;
import qword.domain.dto.Match;
import qword.domain.dto.Name;
import qword.repositories.PlayerRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerServiceTest {

    private static final double TEST_ROUNDING = 0.01d;

    @Test
    @DisplayName("Should calculate the result of a match between two players")
    void shouldCalculateSimpleMatch() {

        // given
        final Player playerHuman = new Player(1, 1000, "AAA");
        final Player playerCPU = new Player(2, 1000, "CPU");
        final PlayerRepository mockRepository = mock(PlayerRepository.class);
        when(mockRepository.getPlayerById(1)).thenReturn(playerHuman);
        when(mockRepository.getPlayerById(2)).thenReturn(playerCPU);
        final PlayerService underTest = new PlayerService(mockRepository, 30, 400, 1000);

        //when
        underTest.addMatch(new Match(2, 1));

        //then
        assertEquals(985d, playerHuman.getRating(), TEST_ROUNDING, "Human player rating is not ok");
        assertEquals(1015d, playerCPU.getRating(), TEST_ROUNDING, "Human player rating is not ok");

        assertEquals(1, playerHuman.getAllMatchResults().size(), "Human player should have played a match");
        assertEquals(0, playerHuman.getAllMatchResults().stream().filter(MatchResult::isWon).count(), "Human player should have won 0 matches");

        assertEquals(1, playerCPU.getAllMatchResults().size(), "CPU player should have played a match");
        assertEquals(1, playerCPU.getAllMatchResults().stream().filter(MatchResult::isWon).count(), "CPU player should have won 1 matches");
    }

    @Test
    @DisplayName("Should calculate the result of a series of matches between two players")
    void shouldCalculateSeriesOfMatches() {

        // given
        final Player playerHuman = new Player(1, 800, "AAA");
        final Player playerCPU = new Player(2, 1200, "CPU");
        final PlayerRepository mockRepository = mock(PlayerRepository.class);
        when(mockRepository.getPlayerById(1)).thenReturn(playerHuman);
        when(mockRepository.getPlayerById(2)).thenReturn(playerCPU);
        final PlayerService underTest = new PlayerService(mockRepository, 32, 400, 1000);

        //when
        underTest.addMatch(new Match(1, 2));
        underTest.addMatch(new Match(2, 1));
        underTest.addMatch(new Match(2, 1));
        underTest.addMatch(new Match(2, 1));

        //then
        assertEquals(817.76d, playerHuman.getRating(), TEST_ROUNDING, "Human player rating is not ok");
        assertEquals(1182.23d, playerCPU.getRating(),TEST_ROUNDING, "Human player rating is not ok");

        final List<MatchResult> humanResults = playerHuman.getAllMatchResults();
        final List<MatchResult> cpuResults = playerCPU.getAllMatchResults();

        assertEquals(4, humanResults.size(), "Human player should have played a match");
        assertEquals(1, humanResults.stream().filter(MatchResult::isWon).count(), "Human player should have won 0 matches");

        assertEquals(4, cpuResults.size(), "CPU player should have played a match");
        assertEquals(3, cpuResults.stream().filter(MatchResult::isWon).count(), "CPU player should have won 1 matches");

        assertEquals(817.76d, humanResults.get(0).getRatingAfterMatch(), TEST_ROUNDING);
        assertEquals(821.39d, humanResults.get(1).getRatingAfterMatch(), TEST_ROUNDING);
        assertEquals(825.16d, humanResults.get(2).getRatingAfterMatch(), TEST_ROUNDING);
        assertEquals(829.09d, humanResults.get(3).getRatingAfterMatch(), TEST_ROUNDING);

        assertEquals(1182.23d, cpuResults.get(0).getRatingAfterMatch(), TEST_ROUNDING);
        assertEquals(1178.60d, cpuResults.get(1).getRatingAfterMatch(), TEST_ROUNDING);
        assertEquals(1174.83d, cpuResults.get(2).getRatingAfterMatch(), TEST_ROUNDING);
        assertEquals(1170.90, cpuResults.get(3).getRatingAfterMatch(), TEST_ROUNDING);
    }

    @Test
    @DisplayName("Should add a new player")
    void shouldAddNewPlayer() {
        final Name inputName = new Name(5, "Balrog");
        final PlayerRepository mockRepository = mock(PlayerRepository.class);
        final ArgumentCaptor<Player> playerCaptor = forClass(Player.class);
        final PlayerService underTest = new PlayerService(mockRepository, 0, 0, 0);

        //when
        underTest.addPlayer(inputName);

        // then
        verify(mockRepository).addPlayer(playerCaptor.capture());
        assertEquals("Balrog", playerCaptor.getValue().getName(), "Player should have correct name");
        assertEquals(5, playerCaptor.getValue().getId(), "Player should have correct name");
    }
}