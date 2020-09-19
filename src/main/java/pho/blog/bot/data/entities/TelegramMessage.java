package pho.blog.bot.data.entities;

import pho.blog.bot.core.handler.MessageType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "telegram_message")
@SequenceGenerator(name = "seq_telegram_msg", sequenceName = "seq_telegram_msg", allocationSize = 1)
public class TelegramMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_telegram_msg")
    private Long id;
    @Column(name = "telegram_id")
    private Integer telegramId;
    @Column(name = "message_type")
    private MessageType messageType;
    @Column(name = "message_text")
    private String messageText;
    @Column(name = "resource_path")
    private String resourcePath;
    @Column(name = "registered_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Date getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Date registerAt) {
        this.registerAt = registerAt;
    }
}
