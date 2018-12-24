package com.tripoin.lsi.thirdparty.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

/**
 * Created on 9/9/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ResponseUpload {
    private String fileName;
    private Path targetLocation;
}
