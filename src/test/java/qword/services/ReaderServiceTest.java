package qword.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qword.domain.EloException;
import qword.domain.dto.Match;
import qword.domain.dto.Name;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReaderServiceTest {

    @Test
    @DisplayName("Should read files and invoke correct import methods")
    void shouldLoadFiles() {
        //given
        final PlayerService mockPlayerService = mock(PlayerService.class);
        final ReaderService underTest = new ReaderService("names", "matches", mockPlayerService);

        //when
        underTest.loadData();

        //then
        verify(mockPlayerService, times(4)).addMatch(any(Match.class));
        verify(mockPlayerService, times(3)).addPlayer(any(Name.class));
    }

    @Test
    @DisplayName("Should throw an EloException if a resource is not readable")
    void shouldThrowEloExceptionWhenResourceNotReadable() {
        //given
        final PlayerService mockPlayerService = mock(PlayerService.class);
        final ReaderService underTest = new ReaderService("names-not-existing", "matches", mockPlayerService);

        //when
        final EloException exception = assertThrows(EloException.class, underTest::loadData);

        // then
        assertEquals("Unable to load data, error: null", exception.getMessage());
    }
}