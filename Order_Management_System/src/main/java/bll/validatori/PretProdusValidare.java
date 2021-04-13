package bll.validatori;
import model.Produs;
/**
 * Aceasta clasa de tip validare verifica daca o inregistare din tabela Produs are un pret corect, adica sa nu fie mai mic de 0
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */
public class PretProdusValidare implements Validator<Produs> {
    public void validare(Produs T){
       if(T.getPret() < 0){
            throw new IllegalArgumentException("Pretul unui produs nu poate fi mai mic de 0 lei");
      }
    }
}