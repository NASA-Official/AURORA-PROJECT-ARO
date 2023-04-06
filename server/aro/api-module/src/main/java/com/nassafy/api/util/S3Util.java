package com.nassafy.api.util;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucket;

    /*
    * multipart 1개를 받아서 s3에 올리는 역할을 하는 함수
    * dirName은 파일의 폴더 경로
    * fileName은 사용자가 정한 uuid + originalName 형식으로 설정
    */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 실패"));

        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    // uploadFilePathAndName는 url에서 공통된 부분을 제외하고, 디렉토리 경로부터 전달
    public String delete(String uploadFilePathAndName) {

        String result = "success";

        try {
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, uploadFilePathAndName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucket, uploadFilePathAndName);
            } else {
                result = "file not found";
            }
        } catch (Exception e) {
            log.debug("Delete File Failed", e);
        }

        return result;
    }

    private String putS3(File uploadFile, String fileName) throws UnsupportedEncodingException {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile).
                        withCannedAcl(CannedAccessControlList.PublicRead)
        );

        String url = amazonS3Client.getUrl(bucket, fileName).toString();

        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("targetfile deletetion successful");
        } else {
            log.info("Failed to delete targetfile");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
