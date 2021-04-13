package bll.validatori;
import bll.validatori.Validator;
import model.Comenzi;
/**
 * Acesata clasa care are rolul de validare verifica daca cantitatea comandata a unei inregistari este in regula, adica sa nu fie mai mica decat 0
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */
public class CantitateComandataValidare implements Validator<Comenzi> {

    public void validare(Comenzi t) {

        if (t.getCantitate_comandata() < 0) {
            throw new IllegalArgumentException("Nu se poate comanda o cantitate mai mica decat 0");
        }
    }

}