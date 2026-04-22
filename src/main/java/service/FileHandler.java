package service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.*;

public class FileHandler {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final Path filePath = Paths.get("C:\\Users\\bobko\\Downloads\\projects2trim\\asd\\asdLab\\university_data.json");

    public void saveAllData(UniversityData data) {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(data);
            Files.writeString(filePath, json);
        } catch (IOException e) {
            System.err.println("Помилка збереження : " + e.getMessage());
        }
    }

    public UniversityData loadAllData() {
        try {
            if (!Files.exists(filePath)) return null;
            String json = Files.readString(filePath);
            return objectMapper.readValue(json, UniversityData.class);
        } catch (IOException e) {
            System.err.println("Помилка завантаження : " + e.getMessage());
            return null;
        }
    }
}

