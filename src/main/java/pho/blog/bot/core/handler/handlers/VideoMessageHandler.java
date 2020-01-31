package pho.blog.bot.core.handler.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Video;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageHandler;
import pho.blog.bot.core.handler.MessageType;

import java.nio.file.Path;
import java.util.logging.Logger;

public class VideoMessageHandler extends MessageHandler {

    private static final Logger LOGGER = Logger.getLogger(VideoMessageHandler.class.getName());

    @Value("${blogbot.files}")
    private String botFiles;

    @Override
    public HandlerResult process(Message message) {
        return message.hasPhoto() ? handle(message) : getNext().process(message);
    }

    private HandlerResult handle(Message message) {
        Video video = message.getVideo();

        LOGGER.warning("\tVideo");

        Path physicalVideo = Path.of(botFiles,
                String.format("%s_%s.mp4", message.getFrom().getId(), video.getFileId()));

        HandlerResult result = new HandlerResult(MessageType.PHOTO, video.getFileId(), physicalVideo.toFile());

        return result;
    }
}
