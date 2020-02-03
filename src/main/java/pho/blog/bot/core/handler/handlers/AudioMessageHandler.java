package pho.blog.bot.core.handler.handlers;

import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Message;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.nio.file.Path;
import java.util.logging.Logger;

public class AudioMessageHandler extends TelegramMessageHandler {
    private static final Logger LOGGER = Logger.getLogger(AudioMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.getAudio() != null ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        Audio audio = message.getAudio();

        LOGGER.warning("\tAudio");

        Path physicalAudio = Path.of(botFiles,
                String.format("%s_%s.ogg", message.getFrom().getId(), audio.getFileId()));

        HandlerResult result = new HandlerResult(MessageType.AUDIO, audio.getFileId(), physicalAudio.toFile());

        return result;
    }
}
