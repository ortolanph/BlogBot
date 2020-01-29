package pho.blog.bot.core.utils;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileUtils {

   private static final String URL_SPEC_TEMPLATE = "https://api.telegram.org/file/bot%s/%s";

    private void saveFile(String fileId, File physicalItem, String botToken, DefaultAbsSender sender) {
        GetFile request = new GetFile();
        request.setFileId(fileId);

        try {
            String uploadedFilePath = sender.execute(request).getFilePath();
            InputStream is = new URL(
                    formatURLSpec(botToken, uploadedFilePath))
                    .openStream();
            org.apache.commons.io.FileUtils.copyInputStreamToFile(is, physicalItem);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatURLSpec(String botToken, String uploadedFilePath) {
        return String.format(URL_SPEC_TEMPLATE, botToken, uploadedFilePath);
    }
}
