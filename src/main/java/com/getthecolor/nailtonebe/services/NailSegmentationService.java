package com.getthecolor.nailtonebe.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class NailSegmentationService {
    private final RestTemplate restTemplate = new RestTemplate();

    public byte[] segmentNails(File imageFile) {
        var url = "http://localhost:5000/segment-nails";
        var fileResource = new FileSystemResource(imageFile);
        var body = new LinkedMultiValueMap<>();
        body.add("image", fileResource);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var requestEntity = new HttpEntity<>(body, headers);
        var response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to segment nails: " + response.getStatusCode());
        }
    }
}
