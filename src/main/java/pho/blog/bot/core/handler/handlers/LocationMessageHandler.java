package pho.blog.bot.core.handler.handlers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageHandler;
import pho.blog.bot.core.handler.MessageType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class LocationMessageHandler extends MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(LocationMessageHandler.class.getName());

    @Value("${blogbot.files}")
    private String botFiles;

    @Override
    public HandlerResult process(Message message) {
        return message.hasPhoto() ? handle(message) : getNext().process(message);
    }

    private HandlerResult handle(Message message) {
        Location location = message.getLocation();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode locationNode = mapper.createObjectNode();
        locationNode.put("latitude", location.getLatitude());
        locationNode.put("longitude", location.getLongitude());

        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(
                            String.format("%s%s_%s.json",
                                    botFiles,
                                    message.getFrom().getId(),
                                    LocalDateTime
                                            .now()
                                            .format(
                                                    DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))
                                            .toString())),
                    locationNode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HandlerResult(MessageType.LOCATION, null);
    }
}
