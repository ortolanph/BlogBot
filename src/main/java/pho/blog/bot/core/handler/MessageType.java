package pho.blog.bot.core.handler;

public enum MessageType {

    PHOTO("Photo"),
    VIDEO("Video"),
    AUDIO("Audio"),
    VOICE("Voice"),
    CONTACT("Contact"),
    LOCATION("Location"),
    STICKER("Sticker"),
    DOCUMENT("Document"),
    TEXT("Text");

    private String description;

    MessageType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
