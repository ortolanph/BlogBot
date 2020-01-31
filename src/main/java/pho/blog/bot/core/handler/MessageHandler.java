package pho.blog.bot.core.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class MessageHandler {

    private MessageHandler next;

    public void setNext(MessageHandler next) {
        this.next = next;
    }

    public MessageHandler getNext() {
        return next;
    }

    public abstract HandlerResult process(Message message);

    // ContactMessageHandler
    // LocationMesageHandler
    // StickerMessageHandler
    // DocumentMessageHandler

}
