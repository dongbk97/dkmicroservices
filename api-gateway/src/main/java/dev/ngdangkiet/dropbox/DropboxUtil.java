package dev.ngdangkiet.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import dev.ngdangkiet.exception.DropboxActionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.cms.MetaData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ngdangkiet
 * @since 12/23/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class DropboxUtil {

    private final DbxClientV2 dropboxClient;

    public void uploadFileToDropbox(MultipartFile multipartFile, String filePath) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            MetaData metaData = MetaData.getInstance(executeAction(() -> {
                try {
                    return dropboxClient.files().uploadBuilder(filePath).uploadAndFinish(inputStream);
                } catch (DbxException | IOException e) {
                    throw new DropboxActionException(String.format("Error when uploading file [%s] with cause [%s]", filePath, e.getMessage()));
                }
            }));
            log.info("Upload success meta data {}", metaData.toString());
            inputStream.close();
        } catch (IOException e) {
            throw new DropboxActionException(String.format("Error when uploading file [%s] with cause [%s]", filePath, e.getMessage()));
        }
    }

    public byte[] downloadFileFromDropbox(String filePath) {
        return executeAction(() -> {
            try {
                return dropboxClient.files().download(filePath).getInputStream().readAllBytes();
            } catch (IOException | DbxException e) {
                throw new DropboxActionException(String.format("Error when downloading file [%s] with cause [%s]", filePath, e.getMessage()));
            }
        });
    }

    private <T> T executeAction(DropboxActionResolver<T> dropboxAction) {
        return dropboxAction.execute();
    }
}
