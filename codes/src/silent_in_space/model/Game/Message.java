package silent_in_space.model.Game;

public abstract class Message {

    private static MessageListener messageListener;

    public static void setMessageListener(MessageListener messageListener) {
        Message.messageListener = messageListener;
    }

    public static void sendGameMessage(String gameMessage) {
        System.out.print(gameMessage);

        if(messageListener != null) {
            messageListener.handle(gameMessage);
        }
    }
}
