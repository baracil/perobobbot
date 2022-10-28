package perobobbot.launcher;

import io.micronaut.runtime.Micronaut;
import io.micronaut.serde.Serde;
import io.micronaut.serde.annotation.SerdeImport;

@SerdeImport(Serde.class)
public class Perobobbot {
    public static void main(String[] args) {
        Micronaut.build(args)
                 .classes(Perobobbot.class)
                 .eagerInitAnnotated(fpc.tools.micronaut.EagerInit.class)
                 .start();
    }
}
