package pho.blog.bot.core.saver;

import java.io.File;
import java.io.InputStream;

public interface Saver {

    void save(InputStream inputStream, File physicalItem) throws Exception;

}
