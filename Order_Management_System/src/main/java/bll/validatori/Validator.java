package bll.validatori;
/**
 * Interfata pentru validatori
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */
public interface Validator<T> {
    public void validare(T t);
}
