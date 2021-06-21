package br.com.diocesesjc.mesce.utils;

import static br.com.diocesesjc.mesce.utils.Constants.BASE_64_PREFIX;
import static br.com.diocesesjc.mesce.utils.Constants.FILE_EXTENSION;
import static br.com.diocesesjc.mesce.utils.Constants.PHOTO_PATH;

import java.io.File;
import java.util.Base64;
import java.util.UUID;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;

public class FileManager {

    @SneakyThrows
    public static String convertToFile(String file) {
        if (Strings.isNotBlank(file)) {
            String base64File = file.replace(BASE_64_PREFIX, Strings.EMPTY);
            String fullPathFile = PHOTO_PATH + UUID.randomUUID() + FILE_EXTENSION;

            byte[] data = Base64.getDecoder().decode(base64File);
            FileUtils.writeByteArrayToFile(new File(fullPathFile), data);

            return fullPathFile;
        }

        return null;
    }

    @SneakyThrows
    public static String convertToBase64(String file) {
        if (Strings.isNotBlank(file)) {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(file));
            return BASE_64_PREFIX + Base64.getEncoder().encodeToString(fileContent);
        }
        return null;
    }
}
