package pho.blog.bot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import pho.blog.bot.data.entities.BlogBotUser;
import pho.blog.bot.data.repositories.BlogBotUserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class BlogBotUserService {

    @Autowired
    private BlogBotUserRepository blogBotUserRepository;

    public boolean register(User user) {
        BlogBotUser blogBotUser = new BlogBotUser();

        BlogBotUser botUser = blogBotUserRepository.findByTelegramId(user.getId());

        if(botUser != null) {
            blogBotUser.setTelegramId(user.getId());
            blogBotUser.setFirstName(user.getFirstName());
            blogBotUser.setLastName(user.getLastName());
            blogBotUser.setUserName(user.getUserName());
            blogBotUser.setRegisterAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

            blogBotUserRepository.save(blogBotUser);

            return true;
        }

        return false;
    }

}
