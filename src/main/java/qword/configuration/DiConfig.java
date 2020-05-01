package qword.configuration;

import qword.EloRankingApp;
import qword.domain.EloException;
import qword.repositories.InMemoryPlayerRepository;
import qword.services.ConsolePresentationService;
import qword.services.PlayerService;
import qword.services.PresentationService;
import qword.services.ReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

/**
 * This class performs dependency injection.
 * Can be removed if a DI framework is adopted.
 */
public class DiConfig {
    private static final String PROPERTIES_FILE = "application.properties";
    private final Properties properties = new Properties();

    private final PlayerService playerService;
    private final ReaderService readerService;
    private final PresentationService presentationService;

    public DiConfig() {
        getProperties();

        final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

        try {
            final int kFactor = parseInt(properties.getProperty("k.factor"));
            final int eloConstant = parseInt(properties.getProperty("elo.constant"));
            final int initialRating = parseInt(properties.getProperty("initial.rating"));
            playerService = new  PlayerService(playerRepository, kFactor, eloConstant, initialRating);
        } catch (final NumberFormatException exception) {
            throw new EloException(String.format("Unable to start the application, error: %s", exception.getMessage()));
        }

        final String namesFile = properties.getProperty("input.names");
        final String matchesFile = properties.getProperty("input.matches");

        readerService = new ReaderService(namesFile, matchesFile, playerService);
        presentationService = new ConsolePresentationService(playerRepository);

    }

    private void getProperties() {
        final InputStream inputStream = EloRankingApp.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        try {
            properties.load(inputStream);
        } catch (final IOException ioException) {
            throw new EloException("Unable to load application properties");
        }
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public ReaderService getReaderService() {
        return readerService;
    }

    public PresentationService getPresentationService() {
        return presentationService;
    }
}
