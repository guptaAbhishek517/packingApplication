package com.packsure.utility;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class BarcodeGenerator {

  
    public static String generateBarcodeImage(String barcodeText, String uploadDir) throws Exception {
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, 300, 100);

        
        String imageDirectory = uploadDir + "/barcodes";  

        // Create the directory if it doesn't exist
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = barcodeText + ".png"; 

        
        Path path = FileSystems.getDefault().getPath(imageDirectory, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

       
        return "barcodes/" + fileName;  
    }
}

