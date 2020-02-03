package pho.blog.bot.core.handler;

import java.io.File;
import java.io.InputStream;

public final class HandlerResult {

    public static final String NO_ID = "NO_ID";

    private final MessageType messageType;
    private final String fileId;
    private final File file;
    private final InputStream inputStream;

    public HandlerResult(MessageType messageType, String fileId, File file) {
        this.messageType = messageType;
        this.fileId = fileId;
        this.file = file;
        this.inputStream = null;
    }

    public HandlerResult(MessageType messageType, InputStream inputStream, File file) {
        this.messageType = messageType;
        this.fileId = null;
        this.file = file;
        this.inputStream = inputStream;
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

    public InputStream getInputStream() {
        return inputStream;
    }
}
