package qword.domain;

import lombok.EqualsAndHashCode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@EqualsAndHashCode(of = "id")
public class Player {

    private final int id;
    private final int initialRating;
    private final String name;
    private final Deque<MatchResult> results;

    public Player(final int id, int initialRating, final String name) {
        this.id = id;
        this.initialRating = initialRating;
        this.name = name;
        this.results = new ArrayDeque<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        if (results.isEmpty()) {
            return initialRating;
        }

        return results.peek().getRatingAfterMatch();
    }

    public void addResult(final MatchResult matchResult) {
        results.push(matchResult);
    }

    public List<MatchResult> getAllMatchResults() {
        return new ArrayList<>(results);
    }
}
