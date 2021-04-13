package start;

import BLC.LogicBusiness;
import bll.ClientBLL;
import bll.ComenziBLL;
import bll.ProdusBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import connection.ConnectionFactory;
import model.Client;
import dao.ClientDAO;
import model.Comenzi;
import model.Produs;
import presentation.PDFgenerator;
import presentation.Parsare;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
/**
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */

public class Start {
    public static void main(String[] args) throws DocumentException, FileNotFoundException {

        String one = args[0];
        ConnectionFactory.getConnection();
        ClientBLL client = new ClientBLL();
        ProdusBLL produs = new ProdusBLL();
        ComenziBLL comenzi = new ComenziBLL();
        List<Client> cl;
        List<Produs> pr;
        List<Comenzi> com;

        cl = client.AfiseazaTot();
        pr = produs.AfiseazaTot();
        com = comenzi.AfiseazaTot();

        int nrClienti = cl.get(cl.size() - 1).getId() + 1;
        int nrProduse = pr.get(pr.size() - 1).getId() + 1;
        int nrComenzi = com.get(com.size() - 1).getId() + 1;
        Parsare parsare = new Parsare(one); //cale: "C:\\fisiereText\\input.txt"
        cl = client.AfiseazaTot();
        pr = produs.AfiseazaTot();
        com = comenzi.AfiseazaTot();

        System.out.println(cl);
        System.out.println(pr);
        System.out.println(com);
        ArrayList<String> l = parsare.imparteComenzi();
        System.out.println(l);
        PDFgenerator pdf = new PDFgenerator();
        LogicBusiness lb = new LogicBusiness(produs, comenzi, client, parsare, pdf);
        lb.control(0);
/*
        client.deleteById(13);
        client.deleteById(14);

        produs.deleteById(12);

        produs.deleteById(14);
        produs.deleteById(15);



 */


        cl=client.AfiseazaTot();
        pr=produs.AfiseazaTot();
        com = comenzi.AfiseazaTot();

        System.out.println(cl);
        System.out.println(pr);
        System.out.println(com);


    }
}
