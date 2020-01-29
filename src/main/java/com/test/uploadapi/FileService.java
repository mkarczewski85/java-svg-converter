package com.test.uploadapi;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public InputStream getInputStream() {
        Path filePath = this.fileStorageLocation.resolve("test.svg").normalize();
        try {
            final InputStream targetStream =
                    new DataInputStream(new FileInputStream(filePath.toFile()));
            return targetStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream getThumbnail() {
        Path filePath = this.fileStorageLocation.resolve("test.svg").normalize();
        try {
            ByteArrayOutputStream rasterized = rasterize(filePath.toFile());
            byte[] bytes = rasterized.toByteArray();
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
//            BufferedImage image = ImageIO.read(input);
//            BufferedImage result = Scalr.resize(image, 200);
            return input;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ByteArrayOutputStream rasterize(File svgFile) throws Exception {
        String svgURIInput = svgFile.toURI().toURL().toString();

        TranscoderInput inputSVGImage = new TranscoderInput(svgURIInput);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        TranscoderOutput outputPNGImage = new TranscoderOutput(pngOutputStream);

        PNGTranscoder myConverter = new PNGTranscoder();
        myConverter.transcode(inputSVGImage, outputPNGImage);

        pngOutputStream.flush();
        return pngOutputStream;
    }

}
