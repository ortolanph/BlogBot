package pho.blog.bot.core.handler.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageHandler;
import pho.blog.bot.core.handler.MessageType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class ContactMessageHandler extends MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(ContactMessageHandler.class.getName());

    @Value("${blogbot.files}")
    private String botFiles;

    @Override
    public HandlerResult process(Message message) {
        return message.hasPhoto() ? handle(message) : getNext().process(message);
    }

    private HandlerResult handle(Message message) {
        Contact contact = message.getContact();

        Path vcardPath = Path.of(botFiles, String.format("%s_%s_%s_contact.vcard", message.getFrom().getId(),
                contact.getFirstName(), contact.getLastName()));

        File vcard = null;

        try {
            vcard = Files.createFile(vcardPath).toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HandlerResult(MessageType.CONTACT, vcard);
    }
}
