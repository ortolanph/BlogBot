package pho.blog.bot.core.handler;

import java.io.File;

public final class HandlerResult {

    public static final String NO_ID = "NO_ID";

    private final MessageType messageType;
    private final String fileId;
    private final File file;

    public HandlerResult(MessageType messageType, String fileId, File file) {
        this.messageType = messageType;
        this.fileId = fileId;
        this.file = file;
    }

    public HandlerResult(MessageType messageType, File file) {
        this.messageType = messageType;
        this.fileId = NO_ID;
        this.file = file;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getFileId() {
        return fileId;
    }

    public File getFile() {
        return file;
    }
}
