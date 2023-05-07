package perobobbot.bom.generator;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.HashSet;

@RequiredArgsConstructor
public class BomReader {

    public static Bom read(URL dependencyList) {
        return new BomReader(dependencyList)._read();
    }

    public static Bom read() {
        return read(BomReader.class.getResource("/dependencies.txt"));
    }

    private final URL dependencyList;

    private Bom _read() {
        try {
            return doRead();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    private Bom doRead() throws IOException {
        final var dependencies = new HashSet<Dependency>();
        try (var is = new BufferedReader(new InputStreamReader(dependencyList.openStream()))) {
            String line = null;
            while((line = is.readLine())!=null) {
                if (line.isBlank() || line.contains("The following files have been resolved")) {
                    continue;
                }
                final String[] tokens = line.trim().split(":");
                if (tokens.length<5) {
                    continue;
                }
                dependencies.add(new Dependency(tokens[0],tokens[1],tokens[3]));
            }
        }
        return new Bom(ImmutableSet.copyOf(dependencies));
    }
}
