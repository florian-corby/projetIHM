package model.Game;

public abstract class Message {

    private static String gameMessage = "";
    private static MessageListener messageListener;

    public static void addGameMessage(String gameMessage) {
        Message.gameMessage = getGameMessage() + gameMessage;
        System.out.print(gameMessage);
    }

    public static void clearGameMessage() { setGameMessage(""); }

    public static String getGameMessage() {
        return gameMessage;
    }

    public static void setGameMessage(String gameMessage) {
        Message.gameMessage = gameMessage;
        System.out.print(gameMessage);
    }

    public static void setMessageListener(MessageListener messageListener) {
        Message.messageListener = messageListener;
    }

    public static void sendGameMessage(String gameMessage) {
        addGameMessage(gameMessage);

        if(messageListener != null) {
            messageListener.update(getGameMessage());
        }

        clearGameMessage();
    }
}
