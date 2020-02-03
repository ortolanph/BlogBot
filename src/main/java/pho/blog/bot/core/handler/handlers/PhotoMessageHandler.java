package pho.blog.bot.core.handler.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class PhotoMessageHandler extends TelegramMessageHandler {

    private static final Logger LOGGER = Logger.getLogger(PhotoMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.hasPhoto() ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        List<PhotoSize> photos = message.getPhoto();

        LOGGER.warning("\tPhoto");

        Path physicalPhoto = Path.of(botFiles, String.format("%s_%s.jpg", message.getFrom().getId(), photos.get(0).getFileId()));

        HandlerResult result = new HandlerResult(MessageType.PHOTO, photos.get(0).getFileId(), physicalPhoto.toFile());

        return result;
    }
}
