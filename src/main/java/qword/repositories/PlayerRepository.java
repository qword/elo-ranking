package qword.repositories;

import qword.domain.Player;

import java.util.List;

public interface PlayerRepository {
    List<Player> getPlayersByRanking();
    List<Player> getPlayersByPosition();
    List<Player> getPlayersByWinsAndLosses();
    Player getPlayerById(int id);
}
