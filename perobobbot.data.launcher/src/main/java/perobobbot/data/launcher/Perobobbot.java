package perobobbot.data.launcher;

import io.micronaut.runtime.Micronaut;

public class Perobobbot {
    public static void main(String[] args) {
        Micronaut.build(args)
                 .classes(Perobobbot.class)
                .eagerInitAnnotated(EagerInit.class)
                 .start()
        ;
    }
}
