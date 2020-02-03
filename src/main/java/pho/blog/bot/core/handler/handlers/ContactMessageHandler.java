package pho.blog.bot.core.handler.handlers;

import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.logging.Logger;

public class ContactMessageHandler extends TelegramMessageHandler {
    private static final Logger LOGGER = Logger.getLogger(ContactMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.hasPhoto() ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        Contact contact = message.getContact();

        Path vcardPath = Path.of(botFiles, String.format("%s_%s_%s_contact.vcard", message.getFrom().getId(),
                contact.getFirstName(), contact.getLastName()));

        String vcard = contact.getVCard();

        InputStream vcardInputStream = IOUtils.toInputStream(vcard, Charset.defaultCharset());

        return new HandlerResult(MessageType.CONTACT, vcardInputStream, vcardPath.toFile());
    }
}
