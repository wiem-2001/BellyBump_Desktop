package tn.esprit.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.esprit.entities.InfoMedicaux;

public class PdfService {

    public void createPdf(File file, InfoMedicaux infoMedicaux) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {



                // Background for header
                contentStream.setNonStrokingColor(new Color(255, 182, 193)); // Light pink
                contentStream.addRect(70, 750, 460, 50);
                contentStream.fill();

                // Title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(100, 760);
                contentStream.showText("Child Medical Information");
                contentStream.endText();

                // Ensuring no null values and formatting content
                String[][] content = {
                        {"Name:", safeString(infoMedicaux.getBabyName())},
                        {"Date:", "2024-05-01"},
                        {"Maladie:", safeString(infoMedicaux.getMaladie())},
                        {"Description:", safeString(infoMedicaux.getDescription())},
                        {"Number of Vaccines:", Integer.toString(infoMedicaux.getNbr_vaccin())},
                        {"Vaccine Date:", infoMedicaux.getDate_vaccin().toString()},
                        {"Blood Type:", safeString(infoMedicaux.getBlood_type())},
                        {"Sickness Estimation:", safeString(infoMedicaux.getSickness_estimation())}
                };

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float startY = 700;
                for (String[] item : content) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, startY);
                    contentStream.showText(item[0] + " " + item[1]);
                    contentStream.endText();
                    startY -= 20;
                }

                // Drawing lines for each field
                contentStream.setStrokingColor(Color.BLACK);
                startY = 695;
                for (int i = 0; i < content.length; i++) {
                    contentStream.moveTo(100, startY);
                    contentStream.lineTo(550, startY);
                    contentStream.stroke();
                    startY -= 20;
                }

                PDImageXObject pdImage = PDImageXObject.createFromFile("src/main/resources/416364332_1325063378185480_6052644255133416633_n.jpg", document);  // Update path as needed
                contentStream.drawImage(pdImage, 50, 700, 50, 50); // x, y, width, height
            }


            document.save(file);
            System.out.println("PDF created successfully.");



        }


        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String safeString(String input) {
        return input == null ? "Not provided" : input;
    }
}
