package pho.blog.bot.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pho.blog.bot.data.entities.BlogBotUser;

@Repository
public interface BlogBotUserRepository extends JpaRepository<BlogBotUser, Long> {

    BlogBotUser findByTelegramId(Integer telegramId);

}
