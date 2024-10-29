package edu.costavitor.setanotificationapi.common;

import com.linuxense.javadbf.DBFReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class DBFUtils {

    public static Integer getNumberOfRecords(DBFReader reader) {

        Integer count = 0;
        while (reader.nextRecord() != null) count++;
        return count;
    }

    public static MultipartFile getStaticNotificationsDbfAsMultipart() throws IOException {
        Resource resource = new ClassPathResource("notifications.dbf");

        try (InputStream inputStream = resource.getInputStream()) {
            return new MockMultipartFile(
                    "file",
                    "notifications.dbf",
                    "multipart/form-data",
                    inputStream
            );
        }
    }
}
