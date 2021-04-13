package BLC;

import bll.ClientBLL;
import bll.ComenziBLL;
import bll.ProdusBLL;
import com.itextpdf.text.DocumentException;
import model.Client;
import model.Comenzi;
import model.Produs;
import presentation.PDFgenerator;
import presentation.Parsare;
/**
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LogicBusiness {
    private ProdusBLL produs;
    private ComenziBLL comanda;
    private ClientBLL client;
    private Parsare parsare;
    private PDFgenerator generator;

    public LogicBusiness(ProdusBLL produs, ComenziBLL comanda, ClientBLL client, Parsare parsare, PDFgenerator generator) {
        this.produs = produs;
        this.comanda = comanda;
        this.client = client;
        this.parsare = parsare;
        this.generator=generator;
    }

    /**
     * Aceasta metoda este una realizata pentru a completa metoda de stergere din AbstractDAO.
     * In momentul in care se doreste stergerea unui client, al carui id este cheie straina pentru o alta tabela din baza de date, apare o eroare.
     * Pentru a evita aceasta eroare am creat o alta metoda care in mometul in care stergem un astfel de client sa stergem si inregistrarea din tabelul in care id-ul clientului este cheie straina
     * @param id_sters id-ul clientului care se doreste a fi sters
     */
    public void deleteForClient(int id_sters) {
        List<Comenzi> lista = comanda.AfiseazaTot();
        for (Comenzi i : lista) {
            if (i.getId_client() == id_sters) {
                comanda.deleteById(i.getId());
            }
        }
        client.deleteById(id_sters);
    }

    /**
     * Aceasta metoda a fost creata pentru a completa metoda din AbstractDAO de stergere a unei inregistrari din tabela Produs
     * In momentul in care dorim stergerea unei inregistari a carui id este cheie straina unei inregistrari din tabela Comezi apare o eroare MySQL
     * Pentru a rezolva aceasta eroare am creat metoda care sterge si comanda respectiva si abia dupa sterge produsul care se doreste sters
     * @param id_sters
     */
    public void deleteForProdus(int id_sters) {
        ArrayList<Comenzi> lista=(ArrayList<Comenzi>)comanda.AfiseazaTot();
        for (Comenzi i : lista) {
            if (i.getId_produs() == id_sters) {
                comanda.deleteById(i.getId());
            }
        }
        produs.deleteById(id_sters);
    }

    /**
     * Aceasta metoda interpreteaza comezile primite de la clasa Parsare(care la randul ei le primeste din fisierul de input)
     * Pe baza comenzii formeaza Clientii, Produsele si Comenzile specifice(ca obiecte Java) daca este cazul
     * Alteori idetifica unele inregistari din baza de date dupa anumite campuri(id, nume) pentru a sterge sau adapta inregistrarile corespunzatoare
     * Dupa care apeleaza metode ca insert, delete, update, metode implemetate in AbstractDAO sau/si genereaza PDF-urile atunvi cand este cazul
     *
     *
     * @param nr: acest int va intra in String-ul din numele PDF-ului generat
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void control(int nr) throws FileNotFoundException, DocumentException {
        List<Client> cl = client.AfiseazaTot();
        List<Produs> pr = produs.AfiseazaTot();
        List<Comenzi> com = comanda.AfiseazaTot();
        int nrClienti = cl.get(cl.size() - 1).getId() + 1, nrProduse = pr.get(pr.size() - 1).getId() + 1, nrComenzi = com.get(com.size() - 1).getId() + 1;
        ArrayList<String> con = new ArrayList<String>();
        con = parsare.imparteComenzi();
        for (int i = 0; i < con.size(); i++){
            String[] tokens = con.get(i).split(": ");
            if(tokens[0].equals("Insert client")){
                String[] arg = tokens[1].split(", ");
                client.insert(new Client(nrClienti++, arg[0], arg[1]));
            }
            if(tokens[0].equals("Delete client")){
                String[] arg = tokens[1].split(", ");
                Client cautat = client.findByNume(arg[0]);
                deleteForClient(cautat.getId());
            }
            if(tokens[0].equals("Order")){
                String[] arg = tokens[1].split(", ");
                Client x = client.findByNume(arg[0]);
                Produs y = produs.findByNume(arg[1]);
                float z = Float.parseFloat(arg[2]);
                Comenzi c = new Comenzi (nrComenzi++, x.getId(), y.getId(),z);
                comanda.insert(c);
                generator.generareBon(c,y,x);

            }
            if(tokens[0].equals("Insert product")){
                String[] arg = tokens[1].split(", ");
                int k=0;
                List<Produs> prodd = produs.AfiseazaTot();
                for(Produs p: prodd){
                    if(arg[0].equals(p.getNume())){
                        k=1;
                        System.out.println("Intru aici");
                        float cant = Float.parseFloat(arg[1]);
                        float cantFinala = p.getCantitate() + cant;
                        List<String> updateProductQuantity = new ArrayList<String>();
                        updateProductQuantity.add("cantitate");
                        updateProductQuantity.add(((Float)(cantFinala)).toString());
                        produs.update(updateProductQuantity, p.getId());
                    }
                }
                if(k==0){
                    float cantt = Float.parseFloat(arg[1]);
                    float pret = Float.parseFloat(arg[2]);
                    produs.insert(new Produs(nrProduse++, arg[0], cantt, pret));
                }

            }
            if(tokens[0].equals("Delete product")){
                //String[] arg = tokens[1].split(", ");
                System.out.println("Ajung la delete produs");
                Produs ppp = produs.findByNume(tokens[1]);
                deleteForProdus(ppp.getId());
            }
            if(tokens[0].equals("Report product")){
                generator.generareReportProdus(produs, nr++);
            }
            if(tokens[0].equals("Report client")){
                generator.generareReportClient(client,nr++);
            }
            if(tokens[0].equals("Report order")){
                generator.generareReportComenzi(comanda,nr++);
            }

        }
    }
}
