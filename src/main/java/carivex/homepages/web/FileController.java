package carivex.homepages.web;


import carivex.homepages.domain.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @GetMapping("/files/{storedName}")
    public ResponseEntity<Resource> download(@PathVariable String storedName,
                                             @RequestParam(name = "name", required = false) String originalName) {
        Path path = fileStorageService.resolve(storedName);
        FileSystemResource resource = new FileSystemResource(path.toFile());
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String filename = (originalName == null || originalName.isBlank()) ? storedName : originalName;
        String disposition = "attachment; filename*=UTF-8''" + java.net.URLEncoder.encode(filename, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition)
                .body(resource);
    }
}
