package qword.readers;

import qword.domain.EloException;
import qword.domain.dto.Match;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class MatchReader extends AbstractReader<Match> {

    public static final String ERROR_TOKEN_NUMBER = "Error parsing input: given fewer than 2 expected tokens";
    public static final String ERROR_PARSING_INPUT = "Error parsing numerical input: expected couple of integers, actual values: %s, %s";

    @Override
    protected final Match createItem(String[] token) {
        if (token == null || token.length < 2) {
            throw new EloException(ERROR_TOKEN_NUMBER);
        }

        try {
            final int winningPlayer = parseInt(token[0]);
            final int losingPlayer = parseInt(token[1]);

            return new Match(winningPlayer, losingPlayer);
        } catch (final NumberFormatException numberFormatException) {
            throw new EloException(format(ERROR_PARSING_INPUT, token[0], token[1]));
        }
    }
}
