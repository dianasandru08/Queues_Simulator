public class Client implements Comparable<Client> {
    private int ID;
    private int arrival;
    private int service;
    private int waiting ;

    public Client(int ID, int arrival, int service) {
        this.ID = ID;
        this.arrival = arrival;
        this.service = service;
    }
    public int getWaiting(){
        return waiting;
    }
    public void setWaiting(int r){
        waiting = r;

    }

    public int getArrival() {
        return arrival;
    }

    public void setArrial(int nou) {
        this.arrival = nou;
    }

    public int getService() {
        return service;
    }

    public void setService(int nou) {
        this.service = nou;
    }

    //metoda suprascrisa pentru a afisa clientii in ordine crescatoare in functie de arrival
    public int compareTo(Client nou) {
        if (arrival > nou.arrival) return 1;
        else if (arrival == nou.arrival) return 0;
        else return -1;

    }
    //metoda toString()
    public String toString(){
        String rez = "";
        rez = "(" + this.ID + ", " + arrival + ", " + service + ", " + waiting + ")";
        return rez;
    }
}
