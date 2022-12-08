package perobobbot.api.bus;

import io.micronaut.core.annotation.NonNull;

public interface Bus extends EventDispatcher {

    int VERSION = 1;

    // "namespace:tenant/topic
    // "perobobbot:command/trigger

    // PR de Godot qui ecoute "perobobbot:command/trigger
    // "godot:command/accepted"
    // "godot:command/refused"
    //
    // Godot ecoute "godot:command/accepted" -> réagir en fonction

    // PR de Plugin Lambda qui ecoute "perobobbot:command/trigger
    // "lambda:command/accepted"
    // "lambda:command/refused"
    //
    // Lambda écoute "godot:command/accepted" -> réagir en fonction

    @NonNull
    void publishEvent(@NonNull String topic, @NonNull Event event);
}
