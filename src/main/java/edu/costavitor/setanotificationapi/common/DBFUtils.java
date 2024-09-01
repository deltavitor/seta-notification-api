package edu.costavitor.setanotificationapi.common;

import com.linuxense.javadbf.DBFReader;

public class DBFUtils {

    public static Integer getNumberOfRecords(DBFReader reader) {

        Integer count = 0;
        while (reader.nextRecord() != null) count++;
        return count;
    }
}
