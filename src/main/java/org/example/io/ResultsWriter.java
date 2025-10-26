package org.example.io;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
public class ResultsWriter {
    public static <T> void writeToJson(String filename, T data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(filename), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}