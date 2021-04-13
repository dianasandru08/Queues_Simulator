package bll;

import dao.AbstractDAO;
import dao.ClientDAO;
import model.Client;

public class ClientBLL extends AbstractBLL<Client> {

    public ClientBLL() {
       // super(abstractDAO);
        super(new ClientDAO());
    }
}
