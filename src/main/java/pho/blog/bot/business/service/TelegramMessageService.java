package pho.blog.bot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.data.entities.TelegramMessage;
import pho.blog.bot.data.repositories.TelegramMessageRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TelegramMessageService {

    @Autowired
    private TelegramMessageRepository telegramMessageRepository;

    public void saveMessage(Integer telegramId, String text, MessageType messageType, String resourcePath) {
        TelegramMessage telegramMessage = new TelegramMessage();

        telegramMessage.setTelegramId(telegramId);
        telegramMessage.setMessageType(messageType);
        telegramMessage.setMessageText(text);
        telegramMessage.setResourcePath(resourcePath);
        telegramMessage.setRegisterAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        telegramMessageRepository.save(telegramMessage);
    }

    public void saveTextMessage(Integer telegramId, String text) {
        TelegramMessage telegramMessage = new TelegramMessage();

        telegramMessage.setTelegramId(telegramId);
        telegramMessage.setMessageType(MessageType.TEXT);
        telegramMessage.setMessageText(text);
        telegramMessage.setRegisterAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        telegramMessageRepository.save(telegramMessage);
    }
}
