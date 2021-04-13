import java.util.ArrayList;
public class Coadă {
    private  int nrCoada;
    private ArrayList<Client> clienti = new ArrayList();
    public Coadă(int nr){
        this.nrCoada= nr;
    }
    public int getServiceCoada(){ //returneaza timpul service pentru clientul care se afla primul la coada;
        int rez = clienti.get(0).getService();
        return rez;
    }
    public void setServiceCoada(int nou){ //seteaza noul timp de servire pentru clientul care se afla primul la coada;
        clienti.get(0).setService(nou);
    }
    public void addClient(Client nou){ //adauga un nou client la coada;
        clienti.add(nou);
    }
    public void clientServit(){ //atunci cand un client este servit este sters din capul cozii;
        clienti.remove(0);
    }
    public int getNrCoada(){ //numarul cozii la care ne aflam;
        return nrCoada;
    }
    public void setNrCoada(int n){
        nrCoada = n;
    }
    public int getServiceTotal(){
        int time = 0;
        for(Client c: clienti){
            time += c.getService();
        }
        return time;
    }
    public int sizeCoada(){
        return clienti.size();
    }
    public Client getClientindex(int index){
        return clienti.get(index);
    }
    public ArrayList<Client> getClienti(){
        return clienti;
    }
    //functia care decrementeaza timpul de Service al clientului din capul cozii pana devine 0, pe urma sterge clientul respectiv
    public void decrementareService(){
        int timpServ = 0;
        int timp=0;
        boolean bool = false;
                   if (!clienti.isEmpty()) {
                       //System.out.println("fac while");
                       if (bool == false) {
                           bool = true;
                           timpServ = clienti.get(0).getService();
                           //System.out.println("ajung aici");
                       }
                       int sum = 0;
                       //System.out.println(this);
                       for(int i = 1; i< clienti.size(); i++) {
                           //System.out.println("Waiting ul este: " + timp);
                           timp = clienti.get(i).getWaiting() + 1;
                           //System.out.println("Waiting ul este: " + timp);
                           clienti.get(i).setWaiting(timp);
                           //System.out.println("pentru clientul"+ clienti.get(i));
                       }
                           timpServ--;
                           clienti.get(0).setService(timpServ);
                           if (timpServ == 0) {
                               //System.out.println(" sau ajung aici");
                               clienti.remove(0);
                               bool = false;
                           }
                   }
    }
    //calculeaza suma pentru timpul de waiting a tuturor clientilor aflati in coada
    public int suma(){
        int sum=0;
        for(Client c: clienti){
            sum = sum + c.getWaiting();
        }
        return sum;
    }
    public int getNrClienti(){
        return  clienti.size();
    }

    public String toString(){
        String rez ="Coada: " + nrCoada + " ";
        if(clienti.isEmpty()) return rez + "inchisa";
        for(Client c: clienti){
            rez = rez + c;
        }
        return rez;
    }



}
