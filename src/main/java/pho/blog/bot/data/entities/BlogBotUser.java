package pho.blog.bot.data.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "blog_bot_user")
@SequenceGenerator(name = "seq_blog_bot_user", sequenceName = "seq_blog_bot_user", allocationSize = 1)
public class BlogBotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_blog_bot_user")
    private Long id;
    @Column(name = "telegram_id")
    private Integer telegramId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Date releaseDate) {
        this.registerAt = releaseDate;
    }
}
