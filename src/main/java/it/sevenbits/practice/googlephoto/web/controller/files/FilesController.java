package it.sevenbits.practice.googlephoto.web.controller.files;

import it.sevenbits.practice.googlephoto.core.model.files.FileInfo;
import it.sevenbits.practice.googlephoto.web.service.files.FilesService;
import it.sevenbits.practice.googlephoto.web.model.ErrorResponse;
import it.sevenbits.practice.googlephoto.web.model.FileNameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller for the files
 */
@Controller
public class FilesController {
    @Autowired
    FilesService storageService;
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("files") List<MultipartFile> files) {
        try {
            File outputFile;
            for (MultipartFile file: files) {
                byte[] bytes = file.getBytes();
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
                outputFile = new File("uploads/" + UUID.randomUUID() +
                        Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")));
                ImageIO.write(image, "png", outputFile);
            }
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<?> deleteImage(@RequestBody FileNameRequest files) {
        List<String> strings = new ArrayList<>();
        try {
            for(String fileName: files.getFiles()) {
                strings.add(fileName.substring(fileName.lastIndexOf("/") + 1));
                Files.delete(Paths.get("uploads/" + fileName.substring(fileName.lastIndexOf("/") + 1)));
            }
        } catch (IOException e) {
            return ResponseEntity.status(404).body(new ErrorResponse("Photo not found" + strings.toString()));
        }
        return ResponseEntity.ok().body("Successful");
    }
}
