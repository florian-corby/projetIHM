package model.Game;

public abstract class Message {

    private static String gameMessage;
    private static MessageListener messageListener;

    public static void addGameMessage(String gameMessage) {
        Message.gameMessage = Message.getGameMessage() + gameMessage;
        System.out.print(gameMessage);
    }

    public static void clearGameMessage() {
        setGameMessage("");
    }

    public static String getGameMessage() {
        return gameMessage;
    }

    public static void setGameMessage(String gameMessage) {
        Message.gameMessage = Message.getGameMessage();
        System.out.print(gameMessage);
    }

    public static void setMessageListener(MessageListener messageListener) {
        Message.messageListener = messageListener;
    }

    public static void sendGameMessage(String gameMessage) {
        Message.addGameMessage(gameMessage);

        if(messageListener != null) {
            messageListener.update(Message.getGameMessage());
        }

        Message.clearGameMessage();
    }
}
