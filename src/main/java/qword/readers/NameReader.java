package qword.readers;

import qword.domain.EloException;
import qword.domain.dto.Name;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class NameReader extends AbstractReader<Name> {

    public static final String ERROR_TOKEN_NUMBER = "Error parsing input: given fewer than 2 expected tokens";
    public static final String ERROR_PARSING_ID = "Error parsing id: expected integer, actual value: %s";

    @Override
    protected final Name createItem(String[] token) {
        if (token == null || token.length < 2) {
            throw new EloException(ERROR_TOKEN_NUMBER);
        }

        try {
            final int id = parseInt(token[0]);

            return new Name(id, token[1]);
        } catch (final NumberFormatException numberFormatException) {
            throw new EloException(format(ERROR_PARSING_ID, token[0]));
        }
    }
}
