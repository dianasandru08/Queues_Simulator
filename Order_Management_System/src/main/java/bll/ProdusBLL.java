package bll;

import dao.AbstractDAO;
import dao.ComenziDAO;
import dao.ProdusDAO;
import model.Produs;


public class ProdusBLL extends AbstractBLL<Produs> {
    public ProdusBLL() {
        super(new ProdusDAO());
    }
}
