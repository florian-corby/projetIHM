package model.Game;

@FunctionalInterface
public interface MessageListener {
    void handle(String message);
}
