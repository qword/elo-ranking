package qword.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;

abstract class AbstractReader<T> {

    private static final String SEPARATOR = "\\s+";

    protected abstract T createItem(final String[] token);

    public final Collection<T> read(final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException();
        }

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return buffer.lines()
                    .map(s -> s.split(SEPARATOR))
                    .map(this::createItem)
                    .collect(Collectors.toList());
        }
    }
}
