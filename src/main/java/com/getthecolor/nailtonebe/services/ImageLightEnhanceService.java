package com.getthecolor.nailtonebe.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageLightEnhanceService {

    public ResponseEntity<byte[]> enhanceImageLight(MultipartFile file) throws IOException {
        var originalImage = ImageIO.read(file.getInputStream());

        var whiteBalancedImage = autoWhiteBalance(originalImage);
        var enhancedImage = autoEnhanceBrightness(whiteBalancedImage);

        var baos = new ByteArrayOutputStream();
        ImageIO.write(enhancedImage, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    private BufferedImage autoEnhanceBrightness(BufferedImage image) {
        var avgBrightness = calculateAverageBrightness(image);
        var target = 128f; // average brightness
        var tolerance = 15f;

        if (Math.abs(avgBrightness - target) <= tolerance) {
            return image;
        }

        var scale = target / avgBrightness;
        scale = Math.max(0.7f, Math.min(scale, 1.3f));

        var result = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB
        );

        var rescaleOp = new RescaleOp(scale, 0, null);
        rescaleOp.filter(image, result);

        return result;
    }

    private float calculateAverageBrightness(BufferedImage image) {
        var sum = 0;
        var width = image.getWidth();
        var height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                // Luminance formula: 0.299*R + 0.587*G + 0.114*B
                var brightness = (int) (0.299 * color.getRed() +
                        0.587 * color.getGreen() +
                        0.114 * color.getBlue());
                sum += brightness;
            }
        }

        return (float) sum / (width * height);
    }


    private BufferedImage autoWhiteBalance(BufferedImage image) {
        var width = image.getWidth();
        var height = image.getHeight();
        var rSum = 0;
        var gSum = 0;
        var bSum = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var color = new Color(image.getRGB(x, y));
                rSum += color.getRed();
                gSum += color.getGreen();
                bSum += color.getBlue();
            }
        }

        var totalPixels = width * height;
        var rAvg = rSum / totalPixels;
        var gAvg = gSum / totalPixels;
        var bAvg = bSum / totalPixels;

        var avgGray = (rAvg + gAvg + bAvg) / 3f;

        var rScale = avgGray / rAvg;
        var gScale = avgGray / gAvg;
        var bScale = avgGray / bAvg;

        var balanced = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var color = new Color(image.getRGB(x, y));
                var r = clamp((int) (color.getRed() * rScale));
                var g = clamp((int) (color.getGreen() * gScale));
                var b = clamp((int) (color.getBlue() * bScale));
                balanced.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return balanced;
    }

    private int clamp(int value) {
        return Math.min(255, Math.max(0, value));
    }
}
