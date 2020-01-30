package pho.blog.bot.core.utils;

import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BotFileUtils {

    private static final String URL_SPEC_TEMPLATE = "https://api.telegram.org/file/bot%s/%s";

    public static InputStream getInputStream(String fileId, String botToken, TelegramLongPollingBot bot) throws Exception {
        GetFile request = new GetFile();
        request.setFileId(fileId);

        try {
            String uploadedFilePath = bot.execute(request).getFilePath();
            return new URL(
                    formatURLSpec(botToken, uploadedFilePath))
                    .openStream();
        } catch (TelegramApiException | MalformedURLException e) {
            throw e;
        }
    }

    public static void saveFile(InputStream inputStream, File physicalItem) throws IOException {
        try {
            FileUtils.copyInputStreamToFile(inputStream, physicalItem);
        } catch (IOException e) {
            throw e;
        }
    }

    private static String formatURLSpec(String botToken, String uploadedFilePath) {
        return String.format(URL_SPEC_TEMPLATE, botToken, uploadedFilePath);
    }
}