package pho.blog.bot.core.handler;

import pho.blog.bot.core.handler.handlers.*;

public final class TelegramMessageHandlerChain {

    private TelegramMessageHandlerChain() {
    }

    public static TelegramMessageHandler chain() {
        TelegramMessageHandler root = new PhotoMessageHandler();

        VideoMessageHandler videoMessageHandler = new VideoMessageHandler();
        root.setNext(videoMessageHandler);

        AudioMessageHandler audioMessageHandler = new AudioMessageHandler();
        videoMessageHandler.setNext(audioMessageHandler);

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