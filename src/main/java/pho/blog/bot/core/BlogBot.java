package pho.blog.bot.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pho.blog.bot.business.service.BlogBotUserService;
import pho.blog.bot.business.service.TelegramMessageService;
import pho.blog.bot.core.handler.HandlerResult;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.core.handler.TelegramMessageHandler;
import pho.blog.bot.core.handler.TelegramMessageHandlerChain;
import pho.blog.bot.core.saver.BotFileUtils;
import pho.blog.bot.core.saver.savers.DefaultFileSaver;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
public class BlogBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(BlogBot.class.getName());
    private static final List<MessageType> COMPLEX_TYPE_MESSAGE_TYPES = Arrays.asList(
            MessageType.PHOTO,
            MessageType.VIDEO,
            MessageType.VIDEO_NOTE,
            MessageType.AUDIO,
            MessageType.VOICE,
            MessageType.STICKER,
            MessageType.DOCUMENT
    );
    @Value("${bot.files}")
    private String botFiles;
    @Value("§{bot.name}")
    private String botName;
    @Value("${BOT_KEY}")
    private String botKey = "";
    @Autowired
    private DefaultFileSaver saver;
    @Autowired
    private BlogBotUserService blogBotUserService;
    @Autowired
    private TelegramMessageService telegramMessageService;

    private TelegramMessageHandler telegramMessageHandler = TelegramMessageHandlerChain.chain();

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String type = null;
        String responseMessage = null;

        if (update.hasMessage()) {
            Message message = update.getMessage();

            LOGGER.warning("Message Received");

            HandlerResult result = telegramMessageHandler.process(message, botFiles);

            if (result != null) {
                try {
                    InputStream inputStream = null;

                    if (COMPLEX_TYPE_MESSAGE_TYPES.contains(result.getMessageType())) {
                        inputStream = BotFileUtils.getInputStream(result.getFileId(), getBotToken(), this);
                    } else {
                        inputStream = result.getInputStream();
                    }

                    BotFileUtils.saveFile(inputStream, result.getFile(), saver);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                telegramMessageService.saveMessage(message.getFrom().getId(), message.getText(), result.getMessageType(), result.getFile().toString());
                type = result.getMessageType().getDescription();
                responseMessage = String.format("%s message received!", type);
            }

            if(message.hasText()) {
                if(type != null) {
                    type = MessageType.TEXT.getDescription();
                }

                if(message.equals("/start")) {
                    blogBotUserService.register(message.getFrom());
                    responseMessage = String.format("Welcome to %s, please refer to https://host/blogs/%d for your blog posts", getBotUsername(), message.getFrom().getId());
                } else {
                    responseMessage = String.format("%s message received!", type);
                }
            }

            if(responseMessage != null) {
                sendResponseMessage(message.getChatId(), responseMessage);
            }
        }
    }

    private void sendResponseMessage(Long chatId, String message) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(message);
        try {
            execute(response);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return botKey;
    }

}
