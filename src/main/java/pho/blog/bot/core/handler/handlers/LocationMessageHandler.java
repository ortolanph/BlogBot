package pho.blog.bot.core.handler.handlers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class LocationMessageHandler extends TelegramMessageHandler {
    private static final Logger LOGGER = Logger.getLogger(LocationMessageHandler.class.getName());

    @Override
    public HandlerResult process(Message message, String botFiles) {
        return message.hasPhoto() ? handle(message, botFiles) : getNext().process(message, botFiles);
    }

    private HandlerResult handle(Message message, String botFiles) {
        Location location = message.getLocation();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode locationNode = mapper.createObjectNode();
        locationNode.put("latitude", location.getLatitude());
        locationNode.put("longitude", location.getLongitude());

        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        InputStream locationInputStream = null;
        File physicalLocation = null;
        try {
            String locationJSON = writer.writeValueAsString(locationNode);
            locationInputStream = IOUtils.toInputStream(locationJSON, Charset.defaultCharset());
            physicalLocation = new File(
                            String.format("%s%s_%s.json",
                                    botFiles,
                                    message.getFrom().getId(),
                                    LocalDateTime
                                            .now()
                                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HandlerResult(MessageType.LOCATION, locationInputStream, physicalLocation);
    }
}
