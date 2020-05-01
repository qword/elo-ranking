package qword.services;

import qword.domain.EloException;
import qword.domain.dto.Match;
import qword.domain.dto.Name;
import qword.readers.MatchReader;
import qword.readers.NameReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class ReaderService {

    private final String namesFile;
    private final String matchesFile;
    private final PlayerService playerService;

    public ReaderService(final String namesFile,
                         final String matchesFile,
                         final PlayerService playerService) {
        this.namesFile = namesFile;
        this.matchesFile = matchesFile;
        this.playerService = playerService;
    }

    public void loadData() {
        try {
            final InputStream namesInputStream = getClass().getClassLoader().getResourceAsStream(namesFile);
            final NameReader nameReader = new NameReader();
            final Collection<Name> nameCollection = nameReader.read(namesInputStream);

            nameCollection.forEach(playerService::addPlayer);

            final InputStream matchesInputStream = getClass().getClassLoader().getResourceAsStream(matchesFile);
            final MatchReader matchReader = new MatchReader();
            final Collection<Match> matchCollection = matchReader.read(matchesInputStream);

            matchCollection.forEach(playerService::addMatch);
        } catch (final IOException exception) {
            throw new EloException(String.format("Unable to load data, error: %s", exception.getMessage()));
        }

    }
}
