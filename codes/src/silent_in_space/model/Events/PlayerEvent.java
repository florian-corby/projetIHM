package silent_in_space.model.Events;

import silent_in_space.model.Characters.Player;

@FunctionalInterface
public interface PlayerEvent {
    void raise(Player p);
}
