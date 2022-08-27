package perobobbot.server.launcher;

import io.micronaut.runtime.Micronaut;

public class Perobobbot {
    public static void main(String[] args) {
        Micronaut.build(args)
//                .banner(false)
                .classes(Perobobbot.class)
                .start()
        ;
    }
}
