package pho.blog.bot.core.handler;

import org.telegram.telegrambots.meta.api.objects.VideoNote;
import pho.blog.bot.core.handler.handlers.*;

public final class TelegramMessageHandlerChain {

    private TelegramMessageHandlerChain() {
    }

    public static TelegramMessageHandler chain() {
        TelegramMessageHandler root = new PhotoMessageHandler();

        VideoMessageHandler videoMessageHandler = new VideoMessageHandler();
        root.setNext(videoMessageHandler);

        VideoNoteMessageHandler videoNoteMessageHandler = new VideoNoteMessageHandler();
        videoMessageHandler.setNext(videoNoteMessageHandler);

        AudioMessageHandler audioMessageHandler = new AudioMessageHandler();
        videoNoteMessageHandler.setNext(audioMessageHandler);

        VoiceMessageHandler voiceMessageHandler = new VoiceMessageHandler();
        audioMessageHandler.setNext(voiceMessageHandler);

        ContactMessageHandler contactMessageHandler = new ContactMessageHandler();
        voiceMessageHandler.setNext(contactMessageHandler);

        LocationMessageHandler locationMessageHandler = new LocationMessageHandler();
        contactMessageHandler.setNext(locationMessageHandler);

        StickerMessageHandler stickerMessageHandler = new StickerMessageHandler();
        locationMessageHandler.setNext(stickerMessageHandler);

        DocumentMessageHandler documentMessageHandler = new DocumentMessageHandler();
        stickerMessageHandler.setNext(documentMessageHandler);

        return root;
    }
}
