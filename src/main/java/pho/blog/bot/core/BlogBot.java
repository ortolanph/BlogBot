package pho.blog.bot.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.TelegramMessageHandler;
import pho.blog.bot.core.handler.TelegramMessageHandlerChain;
import pho.blog.bot.core.saver.BotFileUtils;
import pho.blog.bot.core.saver.savers.DefaultFileSaver;

import java.io.InputStream;
import java.util.logging.Logger;

@Component
public class BlogBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(BlogBot.class.getName());

    @Value("${blogbot.files}")
    private String botFiles;

    @Value("§{blogbot.name}")
    private String botName;

    @Value("${BOT_KEY}")
    private String botKey = "";

    @Autowired
    private DefaultFileSaver saver;

    private TelegramMessageHandler telegramMessageHandler = TelegramMessageHandlerChain.chain();

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String type = "";

            if (update.hasMessage()) {
                Message message = update.getMessage();

                LOGGER.warning("Message Received");

                HandlerResult result = telegramMessageHandler.process(message, botFiles);

                if (result != null) {
                    switch (result.getMessageType()) {
                        case PHOTO:
                        case VIDEO:
                        case AUDIO:
                        case STICKER:
                        case DOCUMENT:
                            try {
                                InputStream inputStream = BotFileUtils.getInputStream(result.getFileId(), getBotToken(), this);
                                BotFileUtils.saveFile(inputStream, result.getFile(), saver);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        case CONTACT:
                        case LOCATION:
                            try {
                                BotFileUtils.saveFile(result.getInputStream(), result.getFile(), saver);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        default:

                    }

                    result.getMessageType().getDescription();
                }

/*            if (message.hasPhoto()) { // OK
                List<PhotoSize> photos = message.getPhoto();

                LOGGER.warning("\tPhoto");

                for (PhotoSize photo : photos) {
                    Path physicalPhoto = Path.of(botFiles,
                            String.format("%s_%s.jpg", message.getFrom().getId(), photo.getFileId()));

                    saveFile(photo.getFileId(), physicalPhoto.toFile());
                }
                type = "Photo";
            }

            if (message.hasVideo()) { // OK
                Video video = message.getVideo();

                LOGGER.warning("\tVideo");

                Path physicalVideo = Path.of(botFiles,
                        String.format("%s_%s.mp4", message.getFrom().getId(), video.getFileId()));

                saveFile(video.getFileId(), physicalVideo.toFile());
                type = "Video";
            }

            if (message.getAudio() != null) { // OK
                Audio audio = message.getAudio();

                LOGGER.warning("\tAudio");

                Path physicalAudio = Path.of(botFiles,
                        String.format("%s_%s.ogg", message.getFrom().getId(), audio.getFileId()));

                saveFile(audio.getFileId(), physicalAudio.toFile());
                type = "Audio";
            }

            if (message.hasContact()) {
                Contact contact = message.getContact();

                Path vcardPath = Path.of(botFiles, String.format("%s_%s_%s_contact.vcard", message.getFrom().getId(),
                        contact.getFirstName(), contact.getLastName()));

                try {
                    File vcard = Files.createFile(vcardPath).toFile();
                    FileUtils.writeStringToFile(vcard, contact.getVCard(), Charset.forName("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                type = "Contact";
            }

            if (message.hasLocation()) {
                Location location = message.getLocation();
                type = "Location";

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
                type = "Location";
            }

            if (message.hasSticker()) {
                Sticker sticker = message.getSticker();

                LOGGER.warning("\tSticker");

                Path physicalSticker = Path.of(botFiles,
                        String.format("%s_%s.webp", message.getFrom().getId(), sticker.getFileId()));

                saveFile(sticker.getFileId(), physicalSticker.toFile());
                type = "Sticker";
            }

            if (message.hasDocument()) {
                Document document = message.getDocument();

                LOGGER.warning("\tDocument");

                Path physicalDocument = Path.of(botFiles,
                        String.format("%s_%s", message.getFrom().getId(), document.getFileName()));

                saveFile(document.getFileId(), physicalDocument.toFile());
                type = "Document";
            }*/

                SendMessage response = new SendMessage();
                response.setChatId(message.getChatId());
                response.setText(type + " Received!");
                try {
                    execute(response);
                } catch (TelegramApiException exception) {
                    exception.printStackTrace();
                }
            }
    }

/*    private void saveFile(String fileId, File physicalItem) {
        GetFile request = new GetFile();
        request.setFileId(fileId);

        try {
            String uploadedFilePath = execute(request).getFilePath();
            InputStream is = new URL(
                    "https://api.telegram.org/file/bot" + getBotToken() + "/" + uploadedFilePath)
                    .openStream();
            FileUtils.copyInputStreamToFile(is, physicalItem);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public String getBotToken() {
        return botKey;
    }

}
