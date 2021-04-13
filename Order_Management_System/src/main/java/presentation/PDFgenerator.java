package presentation;

import bll.ClientBLL;
import bll.ComenziBLL;
import bll.ProdusBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Comenzi;
import model.Produs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;
/**
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */

public class PDFgenerator {
    public PDFgenerator() {
    }

    /**
     * Metoda care genereaza un PDF folosindu-se de datele care se afla in Clasa Comenzi
     * @param com
     * @param ppp produsul din tabela Produs corespunzator Comenzii com
     * @param ccc clientul din tabela Client corespunzatoare Comenzii com
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void generareBon(Comenzi com, Produs ppp, Client ccc) throws FileNotFoundException, DocumentException {
        com.itextpdf.text.Document document = new Document();
        String numepdf = "Boncomanda" +com.getId()+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(numepdf));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        Font font_n = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);
        Paragraph titlu = new Paragraph("\n               BON FISCAL \n", font);
        Paragraph spatiu = new Paragraph(" ");
        Paragraph nrFac = new Paragraph("Numarul de factura: " + com.getId(), font_n);
        Paragraph detProd = new Paragraph("Cod produs: "+ com.getId_produs(), font_n);
        Paragraph detProd_1 = new Paragraph("Nume produs: " + ppp.getNume(), font_n);
        Paragraph detClient = new Paragraph("Client: " + ccc.getNume(), font_n);
        Paragraph cantit = new Paragraph("Cantitate comandata: "+ com.getCantitate_comandata(), font_n);
        //document.addTitle("Factura");
        document.add(titlu);
        document.add(spatiu);
        document.add(nrFac);
        document.add(detClient);
        document.add(detProd);
        document.add(detProd_1);
        document.add(cantit);
        document.close();

    }

    /**
     * Metoda care genereaza un PDF in care se afla tabel cu toate inregistarile clientilor
     * @param client
     * @param nr
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public void generareReportClient(ClientBLL client, int nr) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String nume ="RaportClient"+nr+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(nume));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        Paragraph titlu = new Paragraph ("               RAPORT CLIENTI", font);
        Paragraph spatiu = new Paragraph("\n");
        PdfPTable table = new PdfPTable(3);
        ArrayList<Client> cl = (ArrayList<Client>) client.AfiseazaTot();
        addTableHeader_c(table);
        addRows_c(table, cl);
        document.add(titlu);
        document.add(spatiu);
        document.add(table);
        document.close();
    }

    /**
     * Metoda care adauga randuri in tabelul din PDF utilizand meroda addCell
     * @param table
     * @param cl
     */
    private void addRows_c(PdfPTable table, ArrayList<Client> cl) {
        for(Client c : cl)
        {
            table.addCell("" + c.getId());
            table.addCell(c.getNume());
            table.addCell(c.getAdresa());
        }
    }

    /**
     * Metoda care seteaza header-ele unui tabel precum si caracteristicile acestora
     * @param table
     */
    private void addTableHeader_c(PdfPTable table) {
        Stream.of("ID", "NUME", "ADRESA")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.CYAN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Metoda care genereaza Raportul despre produse, un PDF in care se gaseste un tabel cu toate inregistarile din tabela Produs
     * Se aplica aceeasi pasi ca si la metoda generareReportClient
     * @param produs
     * @param nr
     * @throws DocumentException
     * @throws FileNotFoundException
     */

    public void generareReportProdus(ProdusBLL produs, int nr) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String nume ="RaportProdus"+nr+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(nume));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        Paragraph titlu = new Paragraph ("               RAPORT PRODUSE", font);
        Paragraph spatiu = new Paragraph("\n");
        PdfPTable table = new PdfPTable(4);
        ArrayList<Produs> pr = (ArrayList<Produs>) produs.AfiseazaTot();
        addTableHeader_p(table);
        addRows_p(table, pr);
        document.add(titlu);
        document.add(spatiu);
        document.add(table);
        document.close();
    }

    private void addRows_p(PdfPTable table, ArrayList<Produs> pr) {
        for(Produs i : pr)
        {
            table.addCell("" + i.getId());
            table.addCell(i.getNume());
            table.addCell("" +i.getCantitate());
            table.addCell(""+i.getPret());
        }
    }

    private void addTableHeader_p(PdfPTable table) {
        Stream.of("ID", "NUME/DENUMIRE", "CANTITATE", "PRET")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.CYAN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public void generareReportComenzi(ComenziBLL com, int nr) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String nume ="RaportComenzi"+nr+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(nume));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        Paragraph titlu = new Paragraph ("               RAPORT COMENZI", font);
        Paragraph spatiu = new Paragraph("\n");
        PdfPTable table = new PdfPTable(4);
        ArrayList<Comenzi> cl = (ArrayList<Comenzi>) com.AfiseazaTot();
        addTableHeader_co(table);
        addRows_co(table, cl);
        document.add(titlu);
        document.add(spatiu);
        document.add(table);
        document.close();
    }
    private void addRows_co(PdfPTable table, ArrayList<Comenzi> pr) {
        for(Comenzi i : pr)
        {
            table.addCell("" + i.getId());
            table.addCell(""+i.getId_client());
            table.addCell("" +i.getId_client());
            table.addCell(""+i.getCantitate_comandata());
        }
    }

    private void addTableHeader_co(PdfPTable table) {
        Stream.of("ID", "ID CLIENT", "ID PRODUS", "CANTITATE")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.CYAN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
