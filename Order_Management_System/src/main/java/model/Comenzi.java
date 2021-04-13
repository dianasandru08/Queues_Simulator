package model;

/**
 * Date: April 30-2020
 * Aceasta este o clasa simpla pentru Comenzi
 * @author diana
 * @version 1.0
 *
 */
public class Comenzi {
    private int id;
    private int id_client;
    private int id_produs;
    private float cantitate_comandata;

    /**
     * Constructor cu parametri
     * @param id
     * @param id_c
     * @param id_p
     * @param cant
     */
    public Comenzi(int id, int id_c, int id_p, float cant){
        this.id = id;
        id_client=id_c;
        id_produs=id_p;
        cantitate_comandata = cant;
    }

    /**
     * Constructor fara parametri
     */
    public Comenzi() {
    }

    public void setId(int id_comanda) {
        this.id = id_comanda;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setId_produs(int id_produs) {
        this.id_produs = id_produs;
    }

    public void setCantitate_comandata(float cantitate_comandata) {
        this.cantitate_comandata = cantitate_comandata;
    }

    public int getId() {
        return id;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_produs() {
        return id_produs;
    }

    public float getCantitate_comandata() {
        return cantitate_comandata;
    }
    public String toString(){
        String rez ="";
        rez = "(" + id + ", " + id_client + ", " + id_produs + ", "+cantitate_comandata + ")";
        return rez;
    }
}
