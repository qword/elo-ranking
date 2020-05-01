package qword.domain;

import lombok.Value;

@Value
public class MatchResult {
    Player opponent;
    boolean won;
    double ratingAfterMatch;
}
