package model.Game;

@FunctionalInterface
public interface MessageListener {
    public void update(String message);
}
