package bll;

import bll.validatori.Validator;
import dao.AbstractDAO;
import model.Comenzi;
import model.Produs;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Date: April 30-2020
 * DAO: Data Acces Object
 * Aceasta este o clasa care are in plus fata de AbstractDAO o lista de validatori care verifica daca inregistraile sunt corecte
 * Totodata, aceasta clasa suprascrie metode din AbstractDAO
 * @author diana
 * @version 1.0
 */

public class AbstractBLL<T> {
    private List<Validator<T>> validatori;
    private AbstractDAO abstractDAO;

    public AbstractBLL(AbstractDAO abstractDAO) {
        validatori = new ArrayList<Validator<T>>();
        this.abstractDAO = abstractDAO;
    }
    public T findById(int id) {

        T t = (T) abstractDAO.findById(id);
        if (t == null) {
            throw new NoSuchElementException("id = " + id + "nu a fost gasit!");
        }

        return t;
    }

    public T findByNume(String name) {

        T t = (T) abstractDAO.findByNume(name);
        if (t == null) {
            throw new NoSuchElementException("numele: " + name + "nu a fost gasit!");
        }

        return t;
    }
    public List<T> AfiseazaTot() {

        return abstractDAO.AfiseazaTot();
    }
    public int insert(T t) {
        for (Validator<T> v : validatori) {
            v.validare(t);
        }
        //List<T> lista;
        //lista = abstractDAO.AfiseazaTot();
        //int NR=lista.size()+1;
        return abstractDAO.insert(t);
    }
    public void deleteById(int id) {

        abstractDAO.deleteById(id);
    }
    public void update(List<String> fieldsToUpdate, int rowToUpdate) {
        abstractDAO.updateById(fieldsToUpdate, rowToUpdate);
    }

}
