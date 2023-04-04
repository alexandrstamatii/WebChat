package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.services.IconService;
import com.astamatii.endava.webchat.models.Icon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/icon")
@RequiredArgsConstructor
public class IconController {
    private final IconService iconService;
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getIcon(@PathVariable("id") long id) {
        try {
            // Load the icon from the database
            Icon icon = iconService.getIconById(id);
            if (icon == null) {
                return ResponseEntity.notFound().build();
            }

            // Load the file from the server
            Path path = Paths.get("src/main/resources/static" + icon.getFilePath());
            byte[] bytes = Files.readAllBytes(path);

            // Create a response entity with the file contents
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<Icon> saveIcon(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file to the server
            String filename = file.getOriginalFilename();
            String filePath = "/icons/" + filename;
            Path path = Paths.get("src/main/resources/static" + filePath);
            Files.write(path, file.getBytes());

            // Save the icon to the database
            Icon icon = new Icon();
            icon.setFilePath(filePath);
            iconService.saveIcon(icon);

            // Return a response entity with the saved icon
            return ResponseEntity.ok().body(icon);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
