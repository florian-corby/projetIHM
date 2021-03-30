package model.Events;

import model.Characters.Player;

@FunctionalInterface
public interface PlayerEvent {
    void raise(Player p);
}
