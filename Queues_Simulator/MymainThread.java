import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MymainThread extends Thread {

    private int simulationTime;
    private Operatii o;
    private ArrayList<Coadă> cozi = new ArrayList<Coadă>();
    private int nrClienti;
    private int arrivalMIN;
    private int arrivalMAX;
    private int serviceMIN;
    private int serviceMAX;


    //constructor cu parametri
    public MymainThread(int simulationTime, Operatii o, ArrayList<Coadă> cozi, int n, int n1, int n2, int n3, int n4) {
        this.simulationTime = simulationTime;
        this.o = o;
        this.cozi = cozi;
        this.nrClienti = n;
        this.arrivalMIN = n1;
        this.arrivalMAX = n2;
        this.serviceMIN = n3;
        this.serviceMAX = n4;

    }


    public void run() {
        FileWriter myWrite = null;
        try{ myWrite = new FileWriter ("out-test-1.txt"); } catch(IOException e){ System.out.println("err"); }
        ArrayList<Client> clienti = new ArrayList<Client>(nrClienti);
        clienti = o.generareRandom(nrClienti, arrivalMAX, arrivalMIN, serviceMAX, serviceMIN, clienti);
        Collections.sort(clienti);
        ArrayList<Client> temporar = new ArrayList<Client>();
        try{ myWrite.write("Clientii generati random sunt:" + "\n"); } catch(IOException e){ System.out.println("err"); }
        for (Client un : clienti) { try{ myWrite.write(un + "  "); }
            catch(IOException e){ System.out.println("err"); } }
        try{ myWrite.write("\n  "); } catch(IOException e){ System.out.println("err"); }
        for (int timp = 0; timp <= simulationTime; timp++) {
            try{ myWrite.write("Timp de simulare " + timp + "\n"); } catch(IOException e){ System.out.println("err"); }
            if (!clienti.isEmpty()) {
                if (timp == clienti.get(0).getArrival()) {
                    temporar.add(clienti.get(0));
                    for(int i=1;i<clienti.size();i++){
                       if(clienti.get(0).getArrival() == clienti.get(i).getArrival()){ temporar.add(clienti.get(i)); } }
                    for(Client c: temporar) {
                        int iMin = o.CoadaMinima(cozi); cozi.get(iMin).addClient(c); clienti.remove(0); }
                    temporar.clear();
                }
            }
            try{ myWrite.write(clienti + "\n"); } catch(IOException e){ System.out.println("err"); }
            try{ myWrite.write(cozi + "\n"); } catch(IOException e){ System.out.println("err"); }
            try{ myWrite.write("\n"); } catch(IOException e){ System.out.println("err"); }
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        try{ myWrite.close(); } catch(IOException e){ System.out.println("err"); }
    }
}