package tn.esprit.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.esprit.entities.CartItem;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
public class InvoiceGenerator {

    public static void generateInvoice(String fileName, List<CartItem> cartItems, double total, double tvaRate) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Define the pink color for the background
            Color pinkColor = new Color(255, 228, 225); // Light pink

            // Add the pink background
            try (PDPageContentStream bgContentStream = new PDPageContentStream(document, page)) {
                bgContentStream.setNonStrokingColor(pinkColor);
                bgContentStream.addRect(0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                bgContentStream.fill();
            }

            // Add logo and text content
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                // Add the logo image
                PDImageXObject logo = PDImageXObject.createFromFile("src/main/resources/images/logo.jpg", document);
                contentStream.drawImage(logo, 50, 750, 100, 50); // Adjust position and size as needed

                // Initialize yPosition to start below the logo
                float yPosition = 700; // Adjust this value as needed

                // Draw the company information
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setNonStrokingColor(Color.DARK_GRAY);
                contentStream.setLeading(20); // Set line spacing
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("BellyBump");
                contentStream.newLine();
                contentStream.showText("Petite Ariana (esprit)");
                contentStream.newLine();
                contentStream.showText("Numéro de téléphone : 90 680 241");
                contentStream.endText();

                // Update yPosition after company information
                yPosition -= (3 * 20) + 20; // Assume 3 lines of company info and add some padding

                // Draw the invoice title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Ta Facture");
                contentStream.endText();

                // Update yPosition after invoice title
                yPosition -= 30; // Adjust for space between title and table headers

                // Draw the table headers
                String[] headers = {"Product Name", "Quantity", "Price"};
                float[] columns = {50, 300, 500}; // Adjust column positions as needed
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                for (int i = 0; i < headers.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(columns[i], yPosition);
                    contentStream.showText(headers[i]);
                    contentStream.endText();
                }

                // Draw a line under the header
                contentStream.moveTo(columns[0], yPosition - 10);
                contentStream.lineTo(columns[2] + 100, yPosition - 10); // Extend past the last column
                contentStream.stroke();

                // Update yPosition after header
                yPosition -= 20; // Adjust for space between header and first row

                // Draw the table rows
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                for (CartItem item : cartItems) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(columns[0], yPosition);
                    contentStream.showText(item.getProduct().getNom());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(columns[1], yPosition);
                    contentStream.showText(String.valueOf(item.getQuantity()));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(columns[2], yPosition);
                    contentStream.showText(String.format("$%.2f", item.getProduct().getPrix()));
                    contentStream.endText();

                    yPosition -= 20; // Adjust line height as needed
                }

                // Draw Total and TVA
                contentStream.beginText();
                contentStream.newLineAtOffset(columns[1], yPosition - 20);
                contentStream.showText(String.format("Total: $%.2f", total));
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(columns[1], yPosition - 40);
                contentStream.showText(String.format("TVA: $%.2f", total * 0.2));
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(columns[1], yPosition - 60);
                contentStream.showText(String.format("Total avec TVA: $%.2f", total + total * 0.2));
                contentStream.endText();
            }

            // Save the document
            document.save(fileName);
        }
    }
}
