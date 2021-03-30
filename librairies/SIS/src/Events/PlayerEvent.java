package Events;

import Characters.Player;

@FunctionalInterface
public interface PlayerEvent {
    void raise(Player p);
}
