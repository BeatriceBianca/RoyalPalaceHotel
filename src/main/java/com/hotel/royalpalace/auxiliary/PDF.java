package com.hotel.royalpalace.auxiliary;

import com.hotel.royalpalace.model.Request;
import com.hotel.royalpalace.model.Room;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PDF extends HttpServlet {

    private static final long serialVersionUID = 6067021675155015602L;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public byte[] createPdf(Request requestInfo, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
//---------------------------------------

            Paragraph p = new Paragraph();
            Image img = Image.getInstance("./src/main/resources/static/img/hotel-logo.png");
            img.setAlignment(Image.ALIGN_CENTER);
            img.scaleAbsolute(200,100);
            p.add(img);
            document.add(p);

            Font f = new Font(FontFamily.TIMES_ROMAN,15.0f,Font.BOLD,BaseColor.BLACK);
            Chunk glue = new Chunk(new VerticalPositionMark());

            Phrase phrase = new Phrase();
            p = new Paragraph();
            Chunk chunk1 = new Chunk("\t\t Royal Palace Hotel", f);
            Chunk chunk2 = new Chunk(requestInfo.getCustomer().getLastName()
                    + " " + requestInfo.getCustomer().getFirstName());
            phrase.add(chunk1);
            phrase.add(glue);
            phrase.add(chunk2);
            p.add(phrase);
            document.add(p);


            phrase = new Phrase();
            p = new Paragraph();
            chunk1 = new Chunk("Unirii Boulevard, Bucharest");
            chunk2 = new Chunk(requestInfo.getCustomer().getAddress());
            phrase.add(chunk1);
            phrase.add(glue);
            phrase.add(chunk2);
            p.add(phrase);
            document.add(p);


            phrase = new Phrase();
            p = new Paragraph();
            chunk1 = new Chunk("royalpalace.service@gmail.com");
            chunk2 = new Chunk(requestInfo.getCustomer().getGuestEmail());
            phrase.add(chunk1);
            phrase.add(glue);
            phrase.add(chunk2);
            p.add(phrase);
            document.add(p);


            phrase = new Phrase();
            p = new Paragraph();
            chunk1 = new Chunk("+40727849165");
            chunk2 = new Chunk(requestInfo.getCustomer().getGuestPhone() != null ? requestInfo.getCustomer().getGuestPhone() : "");
            phrase.add(chunk1);
            phrase.add(glue);
            phrase.add(chunk2);
            p.add(phrase);
            document.add(p);

            p = new Paragraph("\n\nInvoice", f);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            p = new Paragraph("( " + dateFormat.format(requestInfo.getArrivalDate())
                    + " - " + dateFormat.format(requestInfo.getDepartureDate()) + " )");
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            document.add(new Paragraph("\n\n"));

            Font f1 = new Font(FontFamily.TIMES_ROMAN,13.0f,Font.BOLD,BaseColor.BLACK);
            PdfPTable table = new PdfPTable(4);

            Phrase cellText = new Phrase("No.",f1);
            PdfPCell cell = new PdfPCell(cellText);
            cell.setMinimumHeight(40f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cellText = new Phrase("Services",f1);
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(40f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cellText = new Phrase("Quantity",f1);
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(40f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cellText = new Phrase("Price (euro)",f1);
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(40f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            List<Room> rooms = new ArrayList<>();
            rooms.addAll(requestInfo.getRooms());

            int nrNo = 0;

            float totalPrice = 0;
            for(int aw = 0; aw < rooms.size(); aw++){
                nrNo++;
                cellText = new Phrase("" + (aw+1));
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                int nrBeds = (rooms.get(aw).getRoomType().getNrDoubleBed() * 2 ) + rooms.get(aw).getRoomType().getNrSingleBed();
                cellText = new Phrase(rooms.get(aw).getRoomType().getRoomName() + " \n " + nrBeds + " beds \n Room Number: " + rooms.get(aw).getRoomNumber());
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                long nrDays = TimeUnit.DAYS.convert(requestInfo.getDepartureDate().getTime()
                        - requestInfo.getArrivalDate().getTime(), TimeUnit.MILLISECONDS);
                cellText = new Phrase("" + nrDays);
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("" + (rooms.get(aw).getRoomType().getPrice() * nrDays));
                totalPrice += rooms.get(aw).getRoomType().getPrice() * nrDays;
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            if (requestInfo.getLateCheckout()) {
                nrNo++;
                cellText = new Phrase("" + nrNo);
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("Late Checkout");
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("" + requestInfo.getRooms().size());
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("" + (requestInfo.getRooms().size()*20));
                totalPrice += requestInfo.getRooms().size()*20;
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            if (requestInfo.getLunch()) {
                nrNo++;
                cellText = new Phrase("" + nrNo);
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("Lunch");
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("" + requestInfo.getNrOfPerson());
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("" + (requestInfo.getNrOfPerson() * 15));
                totalPrice += requestInfo.getNrOfPerson() * 15;
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            if (requestInfo.getDinner()) {
                nrNo++;
                cellText = new Phrase("" + nrNo);
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("Dinner");
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("" + requestInfo.getNrOfPerson());
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
//              --------------------
                cellText = new Phrase("" + (requestInfo.getNrOfPerson() * 10));
                totalPrice += requestInfo.getNrOfPerson() * 10;
                cell = new PdfPCell(cellText);
                cell.setMinimumHeight(25f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);

            document.add(new Paragraph("\n\n"));

            p = new Paragraph("\t\t\t\t\t\tTotal: \t\t " + totalPrice, f1);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
//---------------------------------------

            byte[] pdfBytes = baos.toByteArray();

            document.close();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
            return pdfBytes;
        }
        catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void createPdfAndSendMail(Request requestInfo, HttpServletResponse response) throws ServletException, IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:\\TEMP\\invoice.pdf"));
        document.open();
//---------------------------------------

        Paragraph p = new Paragraph();
        Image img = Image.getInstance("./src/main/resources/static/img/hotel-logo.png");
        img.setAlignment(Image.ALIGN_CENTER);
        img.scaleAbsolute(200,100);
        p.add(img);
        document.add(p);

        Font f = new Font(FontFamily.TIMES_ROMAN,15.0f,Font.BOLD,BaseColor.BLACK);
        Chunk glue = new Chunk(new VerticalPositionMark());

        Phrase phrase = new Phrase();
        p = new Paragraph();
        Chunk chunk1 = new Chunk("\t\t Royal Palace Hotel", f);
        Chunk chunk2 = new Chunk(requestInfo.getCustomer().getLastName()
                + " " + requestInfo.getCustomer().getFirstName());
        phrase.add(chunk1);
        phrase.add(glue);
        phrase.add(chunk2);
        p.add(phrase);
        document.add(p);


        phrase = new Phrase();
        p = new Paragraph();
        chunk1 = new Chunk("Unirii Boulevard, Bucharest");
        chunk2 = new Chunk(requestInfo.getCustomer().getAddress());
        phrase.add(chunk1);
        phrase.add(glue);
        phrase.add(chunk2);
        p.add(phrase);
        document.add(p);


        phrase = new Phrase();
        p = new Paragraph();
        chunk1 = new Chunk("royalpalace.service@gmail.com");
        chunk2 = new Chunk(requestInfo.getCustomer().getGuestEmail());
        phrase.add(chunk1);
        phrase.add(glue);
        phrase.add(chunk2);
        p.add(phrase);
        document.add(p);


        phrase = new Phrase();
        p = new Paragraph();
        chunk1 = new Chunk("+40727849165");
        chunk2 = new Chunk(requestInfo.getCustomer().getGuestPhone() != null ? requestInfo.getCustomer().getGuestPhone() : "");
        phrase.add(chunk1);
        phrase.add(glue);
        phrase.add(chunk2);
        p.add(phrase);
        document.add(p);

        p = new Paragraph("\n\nInvoice", f);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        p = new Paragraph("( " + dateFormat.format(requestInfo.getArrivalDate())
                + " - " + dateFormat.format(requestInfo.getDepartureDate()) + " )");
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        document.add(new Paragraph("\n\n"));

        Font f1 = new Font(FontFamily.TIMES_ROMAN,13.0f,Font.BOLD,BaseColor.BLACK);
        PdfPTable table = new PdfPTable(4);

        Phrase cellText = new Phrase("No.",f1);
        PdfPCell cell = new PdfPCell(cellText);
        cell.setMinimumHeight(40f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cellText = new Phrase("Services",f1);
        cell = new PdfPCell(cellText);
        cell.setMinimumHeight(40f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cellText = new Phrase("Quantity",f1);
        cell = new PdfPCell(cellText);
        cell.setMinimumHeight(40f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cellText = new Phrase("Price (euro)",f1);
        cell = new PdfPCell(cellText);
        cell.setMinimumHeight(40f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        List<Room> rooms = new ArrayList<>();
        rooms.addAll(requestInfo.getRooms());

        int nrNo = 0;

        float totalPrice = 0;
        for(int aw = 0; aw < rooms.size(); aw++){
            nrNo++;
            cellText = new Phrase("" + (aw+1));
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            int nrBeds = (rooms.get(aw).getRoomType().getNrDoubleBed() * 2 ) + rooms.get(aw).getRoomType().getNrSingleBed();
            cellText = new Phrase(rooms.get(aw).getRoomType().getRoomName() + " \n " + nrBeds + " beds \n Room Number: " + rooms.get(aw).getRoomNumber());
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            long nrDays = TimeUnit.DAYS.convert(requestInfo.getDepartureDate().getTime()
                    - requestInfo.getArrivalDate().getTime(), TimeUnit.MILLISECONDS);
            cellText = new Phrase("" + nrDays);
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("" + (rooms.get(aw).getRoomType().getPrice() * nrDays));
            totalPrice += rooms.get(aw).getRoomType().getPrice() * nrDays;
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        if (requestInfo.getLateCheckout()) {
            nrNo++;
            cellText = new Phrase("" + nrNo);
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("Late Checkout");
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("" + requestInfo.getRooms().size());
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("" + (requestInfo.getRooms().size()*20));
            totalPrice += requestInfo.getRooms().size()*20;
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        if (requestInfo.getLunch()) {
            nrNo++;
            cellText = new Phrase("" + nrNo);
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("Lunch");
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("" + requestInfo.getNrOfPerson());
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("" + (requestInfo.getNrOfPerson() * 15));
            totalPrice += requestInfo.getNrOfPerson() * 15;
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        if (requestInfo.getDinner()) {
            nrNo++;
            cellText = new Phrase("" + nrNo);
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("Dinner");
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("" + requestInfo.getNrOfPerson());
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//              --------------------
            cellText = new Phrase("" + (requestInfo.getNrOfPerson() * 10));
            totalPrice += requestInfo.getNrOfPerson() * 10;
            cell = new PdfPCell(cellText);
            cell.setMinimumHeight(25f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        document.add(table);

        document.add(new Paragraph("\n\n"));

        p = new Paragraph("\t\t\t\t\t\tTotal: \t\t " + totalPrice, f1);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
//---------------------------------------

        document.close();

    }


}
