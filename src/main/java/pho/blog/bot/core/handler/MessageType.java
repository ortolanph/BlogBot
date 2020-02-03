package pho.blog.bot.core.handler;

public enum MessageType {

    PHOTO("Photo"),
    VIDEO("Video"),
    AUDIO("Audio"),
    CONTACT("Contact"),
    LOCATION("Location"),
    STICKER("Sticker"),
    DOCUMENT("Document");

    private String description;

    MessageType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
