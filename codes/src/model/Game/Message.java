package model.Game;

import com.sun.javafx.property.adapter.PropertyDescriptor;

public abstract class Message {

    private static String gameMessage;
    private static MessageListener messageListener;

    public static void addGameMessage(String gameMessage) {
        Message.gameMessage = Message.getGameMessage() + gameMessage;
        System.out.println(gameMessage);
    }

    public static void clearGameMessage() {
        setGameMessage("");
    }

    public static String getGameMessage() {
        return gameMessage;
    }

    public static void setGameMessage(String gameMessage) {
        Message.gameMessage = Message.getGameMessage();
        System.out.println(gameMessage);
    }

    public static void setMessageListener(MessageListener messageListener) {
        Message.messageListener = messageListener;
    }

    public static void sendGameMessage(String gameMessage) {
        addGameMessage(gameMessage);

        if(messageListener != null)
            messageListener.update(getGameMessage());

        clearGameMessage();
    }
}
