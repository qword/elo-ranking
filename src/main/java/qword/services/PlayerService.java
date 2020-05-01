package qword.services;

import qword.domain.MatchResult;
import qword.domain.Player;
import qword.domain.dto.Match;
import qword.domain.dto.Name;
import qword.repositories.PlayerRepository;

import static java.lang.Math.pow;

public class PlayerService {

    private final PlayerRepository playerRepository;
    private final int kFactor;
    private final int eloConstant;
    private final int initialRating;

    public PlayerService(final PlayerRepository playerRepository,
                         final int kFactor,
                         final int eloConstant,
                         final int initialRating) {
        this.playerRepository = playerRepository;
        this.kFactor = kFactor;
        this.eloConstant = eloConstant;
        this.initialRating = initialRating;
    }

    public void addPlayer(final Name playerName) {
        Player player = new Player(playerName.getId(), initialRating, playerName.getValue());
        playerRepository.addPlayer(player);
    }

    public void addMatch(final Match match) {
        final Player winningPlayer = playerRepository.getPlayerById(match.getWinningPlayer());
        final Player losingPlayer = playerRepository.getPlayerById(match.getLosingPlayer());

        double winningPlayerRating = winningPlayer.getRating();
        double losingPlayerRating = losingPlayer.getRating();

        double probabilityOfWinA = calculateProbabilityOfWin(winningPlayerRating, losingPlayerRating);
        double probabilityOfWinB = calculateProbabilityOfWin(losingPlayerRating, winningPlayerRating);

        double winningPlayerNewRating = winningPlayerRating + kFactor * (1 - probabilityOfWinA);
        double losingPlayerNewRating = losingPlayerRating - kFactor * probabilityOfWinB;

        final MatchResult winningMatchResult = new MatchResult(winningPlayer, true, winningPlayerNewRating);
        final MatchResult losingMatchResult = new MatchResult(losingPlayer, false, losingPlayerNewRating);

        winningPlayer.addResult(winningMatchResult);
        losingPlayer.addResult(losingMatchResult);
    }

    private double calculateProbabilityOfWin(double playerRating, double opponentRating) {
        return 1d / (1 + pow(10, (opponentRating - playerRating) / eloConstant));
    }
}
