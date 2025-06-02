package com.getthecolor.nailtonebe.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            var response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    byte[].class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Flask service returned non-OK status: " + response.getStatusCode());
            }
        } catch (HttpServerErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Flask service error 500: " + e.getResponseBodyAsString(), e);
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error connecting to Flask service", e);
        }
    }
}
