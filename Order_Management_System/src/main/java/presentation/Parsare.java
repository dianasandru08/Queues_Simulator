package presentation;

import bll.ClientBLL;
import bll.ComenziBLL;
import bll.ProdusBLL;
import model.Comenzi;
import model.Produs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Acesata clasa primeste ca input calea pentru un fisier si citeste din acest fisier comenzi specifice
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */
public class Parsare {
    private String cale;

    public Parsare( String cale) {

        this.cale=cale;

    }

    /**
     * Metoda care imparte in comenzi continutul fisierului, adica de fiecare data cand este apelata se va afisa urmatoarea comanda, urmatorul rand al fisierului
     * @return returneaza un ArrayList de String-uri
     */
    public ArrayList<String> imparteComenzi(){
        ArrayList<String> data = new ArrayList<String>();
        try {
            File myObj = new File(cale);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine());
               // System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("A aparut o eroare la citirea din fisier");
            e.printStackTrace();
        }

        return data;
    }
    public int getNrlinii(){
        int n=0;
        try {
            System.out.println("Ajung aici");
            File myObj = new File(cale);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                n++;
                // System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("A aparut o eroare la citirea din fisier");
            e.printStackTrace();
        }
        return n;
    }
}
