package bll;

import dao.AbstractDAO;
import dao.ComenziDAO;
import model.Comenzi;


public class ComenziBLL extends AbstractBLL<Comenzi> {
    public ComenziBLL() {
        //super(abstractDAO);
        super (new ComenziDAO());
    }
}
