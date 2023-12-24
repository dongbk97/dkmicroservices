package dev.ngdangkiet.controller;

import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dropbox.DropboxUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ngdangkiet
 * @since 12/23/2023
 */

@Tag(name = "File Store", description = "File Store APIs")
@RestController
@RequestMapping("/api/v1/file-store")
@RequiredArgsConstructor
@Slf4j
public class FileStoreController {

    private final DropboxUtil dropboxUtil;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFileToDropbox(@RequestParam(name = "file") MultipartFile multipartFile, @RequestParam(name = "filePath") String filePath) {
        dropboxUtil.uploadFileToDropbox(multipartFile, filePath);
        return ResponseEntity.ok(ApiMessage.SUCCESS);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFileFromDropbox(@RequestParam(name = "filePath") String filePath) {
        var response = dropboxUtil.downloadFileFromDropbox(filePath);
        return ResponseEntity.ok()
                .contentLength(response.length)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header("Content-disposition", "attachment; fileName\"" + filePath + "\"")
                .body(new ByteArrayResource(response));
    }
}
