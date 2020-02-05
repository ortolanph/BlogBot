package pho.blog.bot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pho.blog.bot.core.handler.MessageType;
import pho.blog.bot.data.entities.TelegramMessage;
import pho.blog.bot.data.repositories.TelegramMessageRepository;

import java.time.LocalDateTime;

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
        telegramMessage.setRegisterAt(LocalDateTime.now());

        telegramMessageRepository.save(telegramMessage);
    }
}
