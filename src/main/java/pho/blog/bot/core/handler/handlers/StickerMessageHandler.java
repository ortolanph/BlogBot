package pho.blog.bot.core.handler.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.nio.file.Path;
import java.util.logging.Logger;

public class StickerMessageHandler extends TelegramMessageHandler {

    private static final Logger LOGGER = Logger.getLogger(StickerMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.hasPhoto() ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        Sticker sticker = message.getSticker();

        LOGGER.warning("\tSticker");

        Path physicalSticker = Path.of(botFiles,
                String.format("%s_%s.webp", message.getFrom().getId(), sticker.getFileId()));

        HandlerResult result = new HandlerResult(MessageType.STICKER, sticker.getFileId(), physicalSticker.toFile());

        return result;
    }
}
