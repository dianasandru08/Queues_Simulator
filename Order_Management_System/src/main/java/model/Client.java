package model;

import java.util.Random;

/**
 * Date: April 20-2020
 * Aceasta clasa este o clasa care are aceeasi denumire ca tabela din MySQL si cu aceleasi variabile instanta ca tabela Client
 * @author diana
 * @version 1.0
 *
 */

public class Client {
    /**
     * Id-ul clinetului, prin care se identifica in mod unic, de tip int
     */
    private int id;
    /**
     * Numele clientului, de tip String
     */
    private String nume;
    /**
     * Adresa clientului, de tip String
     */
    private String adresa;

    /**
     * Constructorul clasei
     * @param id
     * @param nume
     * @param adresa
     */
    public Client(int id, String nume, String adresa) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
    }

    /**
     * Constrcutorul clasei fara parametru
     */
    public Client() {
    }

    public Client(String nume, String adresa) {
        this.nume = nume;
        this.adresa = adresa;
    }

    /**
     * Getter id
     * @return id-ul
     */
    public int getId() {
        return id;
    }

    /**
     * Getter pentru nume
     * @return numele
     */

    public String getNume() {
        return nume;
    }

    /**
     * Getter pentru adresa
     * @return adresa
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * Setter pentru id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter pentru nume
     * @param nume
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Setter pentru adresa
     * @param adresa
     */

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     *
     * @return returneaza String-ul de "afisare" pentru un client
     */
    public String toString(){
        String rez="";
        rez = "("+ id + ", " + nume+ ", " + adresa +")";
        return rez;
    }
}
