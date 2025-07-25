package com.example.ex4.services;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    Path uploadDir = Paths.get(System.getProperty("user.dir"),"img-uploads", "uploads");

    public void saveImage(MultipartFile file) throws IOException {

        System.out.println("Directory - " + System.getProperty("user.dir"));

        if (file.isEmpty()) {
            throw new IOException("Cannot store empty file.");
        }

        Files.createDirectories(uploadDir);

        String fileName = file.getOriginalFilename();
        String totalPath = uploadDir.toString() + "/" + fileName;
        File destination = new File(totalPath);
        file.transferTo(destination);
    }

    public UrlResource getImage(String fileName) throws IOException {

        Path filePath = uploadDir.resolve(fileName).normalize();

        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + fileName);
        }

        return new UrlResource(filePath.toUri());
    }

    public boolean deleteImage(String fileName) throws IOException {

        Path filePath = uploadDir.resolve(fileName);
        return Files.deleteIfExists(filePath);
    }

    public String getFullPath(MultipartFile file) {

        return uploadDir.toString() + "/" + file.getOriginalFilename();
    }

}
