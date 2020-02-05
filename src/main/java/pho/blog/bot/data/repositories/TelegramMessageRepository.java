package pho.blog.bot.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pho.blog.bot.data.entities.TelegramMessage;

@Repository
public interface TelegramMessageRepository extends JpaRepository<TelegramMessage, Long> {

}
