import java.util.ArrayList;
import java.util.Random;

public class Operatii {
    private int sumaMAX = 0;
    private int secunda = 0;
    private int minut = 0;
    private int lastTime = 0;
    private int nrAdaugari = 0;
    private int sumaAverage = 0;

    //metoda care genereaza random clientii
    public ArrayList<Client> generareRandom(int nrClienti, int arrivalMAX, int arrivalMin, int serviceMAX, int serviceMin, ArrayList<Client> waitingClients) {
        Random random = new Random();
        int i = 1;
        while(nrClienti > 0) {
            try{
                int randomArrival = random.nextInt((arrivalMAX - arrivalMin) + 1) + arrivalMin;
                int randomService = random.nextInt((serviceMAX - serviceMin) + 1) + serviceMin;

                   waitingClients.add(new Client(i++, randomArrival,randomService));
                   nrClienti--;
            }catch(Exception E) {
                System.out.println("A aparut eroare in generarea random");
            }
        }
        return waitingClients;

    }
    /*
    public ArrayList<MyThread> pornescThreads(int numar) {
        ArrayList<MyThread> threads = new ArrayList<MyThread>();
        for(int i = 0; i < numar; i++) {
            MyThread t = new MyThread(i);
            threads.add(t);
            t.start();
        }
        return threads;
    }
    */
    //metoda care genereaza coda cu timp de asteptare cel mai mic dintr-o lista de cozi
    public int CoadaMinima(ArrayList<Coadă> cozi){ //returneaza indexul cozii pentru care timpul de service este cel mai mic;
        int iMinim = 0;
        int sMin =999999; //cozi.get(0).getSuma();
        for(Coadă c : cozi) {
            if(c.getServiceTotal() <= sMin) {
                sMin = c.getServiceTotal();
                iMinim = c.getNrCoada() - 1;
            }
        }
        return iMinim;
    }
    //metoda implementat si functionabila dar care, din pacate, nu a mai ajuns a fi folosita
    public void sumaTotalaCozi(ArrayList<Coadă> cozi,int timeS, int timeM) {

        int suma = 0;
        for(Coadă a : cozi) {
            suma = suma + a.getServiceTotal();
        }
        if(suma > sumaMAX) {
            sumaMAX = suma;
            secunda = timeS;
            minut = timeM;
        }

    }

}
