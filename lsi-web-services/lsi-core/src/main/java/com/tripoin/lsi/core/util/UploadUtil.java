package com.tripoin.lsi.core.util;

import com.tripoin.lsi.core.data.dto.response.ResponseUpload;
import com.wissensalt.scaffolding.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class UploadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadUtil.class);

    public ResponseUpload doUpload(MultipartFile p_MultiPartFile, String p_UploadPath) throws ServiceException {
        Path path = Paths.get(p_UploadPath).toAbsolutePath().normalize();
        try {
            LOGGER.info("Create Path Dir");
            Files.createDirectories(path);
        } catch (IOException e) {
            LOGGER.error("Error create directory {}", e.toString());
        }
        String fileName = StringUtils.cleanPath(p_MultiPartFile.getOriginalFilename());
        if (fileName.contains("..")) {
            LOGGER.error("File Name Contains Invalid Path Sequence");
            throw new ServiceException("Sorry ! File Name Contains Invalid Path Sequence");
        }

        LOGGER.info("Start Upload");
        Path targetLocation = path.resolve(fileName);
        try {
            Files.copy(p_MultiPartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Done upload ");
        } catch (IOException e) {
            LOGGER.error("Error Uploading File");
        }
        ResponseUpload responseUpload = new ResponseUpload();
        responseUpload.setFileName(fileName);
        responseUpload.setTargetLocation(targetLocation);
        return responseUpload;
    }
}
