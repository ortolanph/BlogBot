package pho.blog.bot.core.handler.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageHandler;
import pho.blog.bot.core.handler.MessageType;

import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class PhotoMessageHandler extends MessageHandler {

    private static final Logger LOGGER = Logger.getLogger(PhotoMessageHandler.class.getName());

    @Value("${blogbot.files}")
    private String botFiles;

    @Override
    public HandlerResult process(Message message) {
        return message.hasPhoto() ? handle(message) : getNext().process(message);
    }

    private HandlerResult handle(Message message) {
        List<PhotoSize> photos = message.getPhoto();

        LOGGER.warning("\tPhoto");

        Path physicalPhoto = Path.of(botFiles, String.format("%s_%s.jpg", message.getFrom().getId(), photos.get(0).getFileId()));

        HandlerResult result = new HandlerResult(MessageType.PHOTO, photos.get(0).getFileId(), physicalPhoto.toFile());

//        for (PhotoSize photo : photos) {
//            Path physicalPhoto = Path.of(botFiles,
//                    String.format("%s_%s.jpg", message.getFrom().getId(), photo.getFileId()));
//
//            saveFile(photo.getFileId(), physicalPhoto.toFile());
//        }
//        type = "Photo";

        return result;
    }
}
