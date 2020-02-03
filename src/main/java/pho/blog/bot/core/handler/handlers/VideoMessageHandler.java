package pho.blog.bot.core.handler.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Video;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.nio.file.Path;
import java.util.logging.Logger;

public class VideoMessageHandler extends TelegramMessageHandler {

    private static final Logger LOGGER = Logger.getLogger(VideoMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.hasPhoto() ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        Video video = message.getVideo();

        LOGGER.warning("\tVideo");

        Path physicalVideo = Path.of(botFiles,
                String.format("%s_%s.mp4", message.getFrom().getId(), video.getFileId()));

        HandlerResult result = new HandlerResult(MessageType.VIDEO, video.getFileId(), physicalVideo.toFile());

        return result;
    }
}
