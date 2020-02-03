package pho.blog.bot.core.handler.handlers;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
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
        return message.hasContact() ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        Contact contact = message.getContact();

        Path vcardPath = Path.of(botFiles, String.format("%s_%s_%s_contact.vcard", message.getFrom().getId(),
                contact.getFirstName(), contact.getLastName()));

        String vcard = contact.getVCard();

        if(vcard == null) {
            VCard myContact = new VCard();

            StructuredName name = new StructuredName();
            name.setFamily(contact.getLastName());
            name.setGiven(contact.getFirstName());
            myContact.setStructuredName(name);

            Telephone telephone = new Telephone(contact.getPhoneNumber());
            myContact.addTelephoneNumber(telephone);

            myContact.setFormattedName(String.format("%s %s", contact.getFirstName(), contact.getLastName()));

            vcard = Ezvcard.write(myContact).version(VCardVersion.V4_0).go();
        }

        LOGGER.warning("\tContact");

        InputStream vcardInputStream = IOUtils.toInputStream(vcard, Charset.defaultCharset());

        return new HandlerResult(MessageType.CONTACT, vcardInputStream, vcardPath.toFile());
    }
}
