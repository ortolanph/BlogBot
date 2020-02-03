package pho.blog.bot.core.handler.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.nio.file.Path;
import java.util.logging.Logger;

public class VideoNoteMessageHandler extends TelegramMessageHandler {

    private static final Logger LOGGER = Logger.getLogger(VideoNoteMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.hasVideoNote() ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        VideoNote video = message.getVideoNote();

        LOGGER.warning("\tVideo Note");

        Path physicalVideoNote = Path.of(botFiles,
                String.format("%s_%s.mp4", message.getFrom().getId(), video.getFileId()));

        HandlerResult result = new HandlerResult(MessageType.VIDEO_NOTE, video.getFileId(), physicalVideoNote.toFile());

        return result;
    }
}
