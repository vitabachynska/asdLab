package service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.DataPersistenceException;
import exceptions.InvalidDataException;

import java.io.IOException;
import java.nio.file.*;

public class FileHandler {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final Path filePath = Paths.get("C:\\Users\\bobko\\Downloads\\projects2trim\\asd\\university_data.json");

    public void saveAllData(UniversityData data) throws DataPersistenceException {
        try {
            if (data == null) {
                throw new InvalidDataException("Помилка. Немає університетських даних");
            }
            String json = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(data);
            Files.writeString(filePath, json);
        } catch (IOException e) {
            throw new DataPersistenceException("Помилка збереження у файл : " + e.getMessage(), e);
        } catch (InvalidDataException e) {
            throw new DataPersistenceException("Помилка у збереженні даних : " + e.getMessage(), e);
        }
    }

    public UniversityData loadAllData() throws DataPersistenceException {
        try {
            if (!Files.exists(filePath)) {
                return null;
            }
            String json = Files.readString(filePath);
            if (json == null || json.isEmpty()) {
                throw new InvalidDataException("Файл не містить даних або пошкоджений");
            }
            return objectMapper.readValue(json, UniversityData.class);
        } catch (IOException e) {
            throw new DataPersistenceException("Помилка у завантаженні даних з файлу: " + e.getMessage(), e);
        } catch (InvalidDataException e) {
            throw new DataPersistenceException("Дані відсутні або пошкодженні у файлі: " + e.getMessage(), e);
        }
    }
}

