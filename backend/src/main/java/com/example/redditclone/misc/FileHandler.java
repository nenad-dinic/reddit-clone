package com.example.redditclone.misc;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileHandler {

    public static String saveFile(MultipartFile file) {
        String name = UUID.randomUUID().toString();
        Path uploadDirectory = Paths.get("./uploads");
        if (!Files.exists(uploadDirectory)) {
            try {
                Files.createDirectories(uploadDirectory);
            } catch (IOException e) {
                return null;
            }
        }
        String originalName = file.getOriginalFilename();
        Path filePath = Paths.get("./uploads", name + originalName.substring(originalName.lastIndexOf(".")));
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            return null;
        }
        return filePath.toString().substring(1).replaceAll("\\\\", "/");
    }
}
