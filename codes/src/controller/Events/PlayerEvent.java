package controller.Events;

import controller.Characters.Player;

@FunctionalInterface
public interface PlayerEvent {
    void raise(Player p);
}
