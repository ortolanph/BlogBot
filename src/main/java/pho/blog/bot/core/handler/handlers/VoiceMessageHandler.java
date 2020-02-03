package pho.blog.bot.core.handler.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Voice;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.nio.file.Path;
import java.util.logging.Logger;

public class VoiceMessageHandler extends TelegramMessageHandler {
    private static final Logger LOGGER = Logger.getLogger(VoiceMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return (message.getVoice() != null) ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        Voice voice = message.getVoice();

        LOGGER.warning("\tVoice");

        Path physicalVoice = Path.of(botFiles,
                String.format("%s_%s.ogg", message.getFrom().getId(), voice.getFileId()));

        HandlerResult result = new HandlerResult(MessageType.AUDIO, voice.getFileId(), physicalVoice.toFile());

        return result;
    }
}
