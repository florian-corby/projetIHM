package controller.Events;

import controller.Characters.PlayerController;

@FunctionalInterface
public interface PlayerEvent {
    void raise(PlayerController p);
}
