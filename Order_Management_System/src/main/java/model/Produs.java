package model;

/**
 * Date: April 30-2020
 * Aceasta este o clasa simpla pentru a "retine" Produsele
 * @author diana
 * @version 1.0
 */
public class Produs {
    private int id;
    private String nume;
    private float cantitate;
    private float pret;

    /**
     * Constructorul cu parametri
     * @param id
     * @param nume
     * @param cantitate
     * @param pret
     */
    public Produs(int id,  String nume, float cantitate, float pret){
        this.id = id;
        this.nume= nume;
        this.cantitate=cantitate;
        this.pret=pret;
    }

    /**
     * Constructor fara parametri
     */

    public Produs(){

    }

    /**
     * Getter pentru id-ul produsului
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter pentru numele produsului
     * @return numele ca String
     */
    public String getNume() {
        return nume;
    }

    /**
     * Setter pentru produs
     * @param id_produs de tip int
     */
    public void setId(int id_produs) {
        this.id = id_produs;
    }

    /**
     * Setter pentru numele produsului
     * @param nume de tip String
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Setter pentru cantitate
     * @param cantitate de tip float
     */
    public void setCantitate(float cantitate) {
        this.cantitate = cantitate;
    }

    /**
     * Setter pentru pret
     * @param pret de tip float
     */
    public void setPret(float pret) {
        this.pret = pret;
    }

    /**
     * Getter pentru cantitate
     * @return cantitate ca float
     */
    public float getCantitate() {
        return cantitate;
    }

    /**
     * Getter pentru pret
     * @return pretul ca float
     */
    public float getPret() {
        return pret;
    }

    /**
     * Metoda toString()
     * @return String-ul de afisare a produsului
     */
    public String toString(){
        String rez="";
        rez ="("+ id + ", " + nume + ", "+ cantitate + ", " + pret +")";
        return rez;
    }
}
