package silent_in_space.model.Game;

@FunctionalInterface
public interface MessageListener {
    void handle(String message);
}
