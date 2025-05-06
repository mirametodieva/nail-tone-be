package com.getthecolor.nailtonebe.controllers;


import com.getthecolor.nailtonebe.services.ImageLightEnhanceService;
import com.getthecolor.nailtonebe.services.NailSegmentationService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageLightEnhanceService imageLightEnhanceService;
    private final NailSegmentationService nailSegmentationService;

    public ImageController(ImageLightEnhanceService imageLightEnhanceService,
                           NailSegmentationService nailSegmentationService) {
        this.imageLightEnhanceService = imageLightEnhanceService;
        this.nailSegmentationService = nailSegmentationService;
    }

    @PostMapping("/light-enhance")
    public ResponseEntity<byte[]> enhanceImageLight(@RequestParam("file") MultipartFile file) throws IOException {
        return imageLightEnhanceService.enhanceImageLight(file);
    }

    @PostMapping("/segment-nails")
    public ResponseEntity<byte[]> segmentNails(@RequestParam("file") MultipartFile file) throws IOException {
        var tempFile = File.createTempFile("upload_", ".jpg");
        file.transferTo(tempFile);

        var result = nailSegmentationService.segmentNails(tempFile);
        tempFile.delete();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDisposition(ContentDisposition.attachment().filename("nails_only.png").build());

        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }


}

