package pho.blog.bot.core.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class TelegramMessageHandler {

    private TelegramMessageHandler next;

    public TelegramMessageHandler getNext() {
        return next;
    }

    public void setNext(TelegramMessageHandler next) {
        this.next = next;
    }

    public abstract HandlerResult process(Message message, String botFiles);

}
