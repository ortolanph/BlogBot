package pho.blog.bot.core.handler.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Message;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageHandler;
import pho.blog.bot.core.handler.MessageType;

import java.nio.file.Path;
import java.util.logging.Logger;

public class AudioMessageHandler extends MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(AudioMessageHandler.class.getName());

    @Value("${blogbot.files}")
    private String botFiles;

    @Override
    public HandlerResult process(Message message) {
        return message.hasPhoto() ? handle(message) : getNext().process(message);
    }

    private HandlerResult handle(Message message) {
        Audio audio = message.getAudio();

        LOGGER.warning("\tAudio");

        Path physicalAudio = Path.of(botFiles,
                String.format("%s_%s.ogg", message.getFrom().getId(), audio.getFileId()));

        HandlerResult result = new HandlerResult(MessageType.AUDIO, audio.getFileId(), physicalAudio.toFile());

        return result;
    }
}
