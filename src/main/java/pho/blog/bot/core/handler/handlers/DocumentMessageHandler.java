package pho.blog.bot.core.handler.handlers;

import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.nio.file.Path;
import java.util.logging.Logger;

public class DocumentMessageHandler extends TelegramMessageHandler {

    private static final Logger LOGGER = Logger.getLogger(DocumentMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.hasDocument() ? handle(message, botFiles) : null;
    }

    private HandlerResult handle(Message message, String botFiles) {
        Document document = message.getDocument();

        LOGGER.warning("\tDocument");

        Path physicalDocument = Path.of(botFiles,
                String.format("%s_%s", message.getFrom().getId(), document.getFileName()));

        HandlerResult result = new HandlerResult(MessageType.DOCUMENT, document.getFileId(), physicalDocument.toFile());

        return result;
    }
}
