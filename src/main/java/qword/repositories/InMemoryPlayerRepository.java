package qword.repositories;

import qword.domain.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class InMemoryPlayerRepository implements PlayerRepository {

    private final Map<Integer, Player> players = new HashMap<>();

    @Override
    public List<Player> getPlayersByRanking() {
        return players.values().stream()
                .sorted(comparingDouble(Player::getRating).reversed())
                .collect(toList());
    }

    @Override
    public List<Player> getPlayersByScore() {
        // TODO: check if the sort by score/position has a different meaning than the sort by ranking
        return getPlayersByRanking();
    }

    @Override
    public List<Player> getPlayersByWinsAndLosses() {
        return players.values().stream()
                .sorted(comparingInt(Player::getWinLossesDifference).reversed())
                .collect(toList());
    }

    @Override
    public Player getPlayerById(int id) {
        return players.get(id);
    }

    @Override
    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }
}
