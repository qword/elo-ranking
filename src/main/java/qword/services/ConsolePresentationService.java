package qword.services;

import qword.domain.Player;
import qword.repositories.PlayerRepository;

import java.util.List;

import static java.lang.String.format;

public class ConsolePresentationService implements PresentationService {

    private final PlayerRepository playerRepository;

    public ConsolePresentationService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void showPlayer(int id) {
        final Player player = playerRepository.getPlayerById(id);
        if (player != null) {
            System.out.println(format("Id: %s%nName: %s%nRating: %.2f", player.getId(), player.getName(), player.getRating()));
        } else {
            System.out.println("Player not found");
        }
    }

    @Override
    public void showPlayerHistory(int id) {
        final Player player = playerRepository.getPlayerById(id);
        if (player != null) {
            System.out.println(format("Id: %s, name: %s", player.getId(), player.getName()));
            player.getAllMatchResults().forEach(match ->
                    System.out.println(format("Opponent: %s, won: %b, rating after match: %.2f",
                            match.getOpponent().getName(), match.isWon(), match.getRatingAfterMatch())));
        } else {
            System.out.println("Player not found");
        }
    }

    @Override
    public void showPlayersByRanking() {
        final List<Player> players = playerRepository.getPlayersByRanking();
        int pos = 1;

        for (final Player player : players) {
            System.out.println(
                    format("Position %03d, name: %s, rating: %.2f", pos++, player.getName(), player.getRating()));
        }
    }

    @Override
    public void showPlayersByWinsAndLosses() {
        final List<Player> players = playerRepository.getPlayersByWinsAndLosses();
        int pos = 1;

        for (final Player player : players) {
            System.out.println(
                    format("Position %03d, name: %s, wins/losses: %d",
                            pos++, player.getName(), player.getWinLossesDifference()));
        }
    }
}
