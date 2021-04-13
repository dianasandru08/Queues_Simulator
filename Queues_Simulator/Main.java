import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javafx.scene.chart.ScatterChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        try{
            String one = args[0];
            String two = args[1];
        }
        catch(Exception ex){
            System.out.printf("Err");
        }


        int nrClienti = 0;
        int nrCozi = 0;
        int simulationTime = 0;
        int arrivalMIN = 0, arrivalMAX = 0, serviceMIN = 0, serviceMAX = 0;
        try {
            String s1, s2;
            File myObj = new File("C:\\fisiereText\\in-test-1.txt");
            Scanner n = new Scanner(myObj);
            nrClienti = n.nextInt();
            nrCozi = n.nextInt();
            simulationTime = n.nextInt();
            n.nextLine();
            s1 = n.nextLine();
            s2 = n.nextLine();

            String[] ss1 = s1.split(",", 2);
            String[] ss2 = s2.split(",", 2);

            arrivalMIN = Integer.parseInt(ss1[0]);
            arrivalMAX = Integer.parseInt(ss1[1]);
            serviceMIN = Integer.parseInt(ss2[0]);
            serviceMAX = Integer.parseInt(ss2[1]);

            n.close();


        } catch (Exception ex) {
            System.out.println("Erroare");
        }

        System.out.println(nrClienti);
        System.out.println(nrCozi);
        System.out.println(simulationTime);
        System.out.println(arrivalMIN);
        System.out.println(arrivalMAX);
        System.out.println(serviceMIN);
        System.out.println(serviceMAX);


        /*
        //FileWriter my = new FileWriter("FisierOut.txt");
        Operatii o = new Operatii();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("Generarea randome a clientilor: ");
        // my.write("Generare randome a clientilor: "+"\n");
        System.out.println("Numarul de clineti este: ");
        //Scanner in = new Scanner(System.in);
        //nrClienti = in.nextInt();
        ArrayList<Client> clienti = new ArrayList<Client>(nrClienti);
        clienti = o.generareRandom(nrClienti, arrivalMAX, arrivalMIN, serviceMAX, serviceMIN, clienti);
        Collections.sort(clienti);
        for (Client un : clienti) {
            System.out.println(un);
            //my.write(un+ "\n");
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        System.out.println("Numarul de cozi este: ");

         */
        ArrayList<Coadă> cozi = new ArrayList<Coadă>();
        Operatii o = new Operatii();
        for (int j = 1; j <= nrCozi; j++) {
            cozi.add(new Coadă(j));
            System.out.println(cozi.get(j - 1).getNrCoada()); //afisam numarul cozii;

        }

        MymainThread principal = new MymainThread(simulationTime,o, cozi, nrClienti, arrivalMIN, arrivalMAX, serviceMIN, serviceMAX );
        //MyThread thread= new MyThread(1, cozi.get(0) , simulationTime);
        //MyThread thread1= new MyThread(2, cozi.get(1), simulationTime );
        //principal.start();
        int average = 0;
        for (int j = 1; j <= nrCozi; j++) {
            MyThread t = new MyThread(j, cozi.get(j - 1));
            t.start();
        }
        principal.start();


    }
}
