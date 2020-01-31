package pho.blog.bot.core.saver.savers;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import pho.blog.bot.core.saver.Saver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DefaultFileSaver implements Saver {

    @Override
    public void save(InputStream inputStream, File physicalItem) throws Exception {
        try {
            FileUtils.copyInputStreamToFile(inputStream, physicalItem);
        } catch (IOException e) {
            throw e;
        }
    }
}
