package qword.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qword.domain.Player;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InMemoryPlayerRepositoryTest {

    @Test
    @DisplayName("Should add a list of players and return them sorted by ranking")
    void shouldAddPlayersAndSortByRanking() {

        // given
        final Player player1 = new Player(1, 9, "Blanka");
        final Player player2 = new Player(2, 1, "E-Honda");
        final Player player3 = new Player(3, 3, "Dhalsim");
        final Player player4 = new Player(4, 99, "Mr. Bison");

        // when
        final PlayerRepository underTest = new InMemoryPlayerRepository();
        underTest.addPlayer(player1);
        underTest.addPlayer(player2);
        underTest.addPlayer(player3);
        underTest.addPlayer(player4);

        // then
        final List<Player> expectedOrder = List.of(player4, player1, player3, player2);
        assertEquals(expectedOrder, underTest.getPlayersByRanking(), "Should be sorted by ranking");
    }

    @Test
    @DisplayName("Should add a list of players and return them sorted by score")
    void shouldAddPlayersAndSortByScore() {

        // given
        final Player player1 = new Player(1, 9, "Blanka");
        final Player player2 = new Player(2, 1, "E-Honda");
        final Player player3 = new Player(3, 3, "Dhalsim");
        final Player player4 = new Player(4, 99, "Mr. Bison");

        // when
        final PlayerRepository underTest = new InMemoryPlayerRepository();
        underTest.addPlayer(player1);
        underTest.addPlayer(player2);
        underTest.addPlayer(player3);
        underTest.addPlayer(player4);

        // then
        final List<Player> expectedOrder = List.of(player4, player1, player3, player2);
        assertEquals(expectedOrder, underTest.getPlayersByRanking(), "Should be sorted by ranking");
    }

    @Test
    @DisplayName("Should add a list of players and return them sorted by the wins-losses difference")
    void shouldAddPlayersAndSortByWinsAndLosses() {

        // given
        final Player player1 = mockPlayer(1, 4);
        final Player player2 = mockPlayer(2, -6);
        final Player player3 = mockPlayer(3, 33);
        final Player player4 = mockPlayer(4, 0);

        // when
        final PlayerRepository underTest = new InMemoryPlayerRepository();
        underTest.addPlayer(player1);
        underTest.addPlayer(player2);
        underTest.addPlayer(player3);
        underTest.addPlayer(player4);

        // then
        final List<Player> expectedOrder = List.of(player3, player1, player4, player2);
        assertEquals(expectedOrder, underTest.getPlayersByWinsAndLosses(), "Should be sorted by ranking");
    }

    private Player mockPlayer(final int id, final int winLosses) {
        final Player player = mock(Player.class);
        when(player.getId()).thenReturn(id);
        when(player.getWinLossesDifference()).thenReturn(winLosses);
        return player;
    }

}