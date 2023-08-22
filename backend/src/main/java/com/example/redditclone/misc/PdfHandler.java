package com.example.redditclone.misc;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PdfHandler {

    public String getText(File file) {
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripper stripper = new PDFTextStripper();
            PDDocument pdDocument = parser.getPDDocument();
            String text = stripper.getText(pdDocument);
            pdDocument.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
