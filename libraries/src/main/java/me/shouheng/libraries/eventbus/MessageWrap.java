package me.shouheng.libraries.eventbus;

/**
 * @author shouh
 * @version $Id: MessageWrap, v 0.1 2018/8/5 15:18 shouh Exp$
 */
public class MessageWrap {

    public final String message;

    public static MessageWrap getInstance(String message) {
        return new MessageWrap(message);
    }

    private MessageWrap(String message) {
        this.message = message;
    }
}
