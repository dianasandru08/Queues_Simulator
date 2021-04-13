import java.util.ArrayList;

public class MyThread extends Thread {
        private int threadIndex;
        private Coadă c;


       public  MyThread(int threadIndex, Coadă c) {

            this.threadIndex = threadIndex;
            this.c =c;

        }
        public void  run() {
             int ad = 0;
            System.out.println("Intru in thread ul mic");
            for(int t= 0; t<=60 ; t++){

                synchronized(c) {
                    c.decrementareService();
                    ad = ad +c.suma();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            System.out.println(ad);

        }



            /*int time;
            int timpServ = 0;
            boolean bool = false;

            for(time = 0 ; time <= simulationTime; time++){
                if(!clienti.isEmpty()){
                    if (time >= clienti.get(0).getArrival()) {
                        if (bool == false) {
                            bool = true;
                            c.addClient(clienti.get(0));
                            System.out.println(c.getClienti());
                            timpServ = c.getClientindex(0).getService();

                        }
                        System.out.println(timpServ);
                        if (timpServ == 0) {
                            c.clientServit();
                            bool = false;
                            clienti.remove(0);
                        }
                        timpServ--;


                    }

                }

            }

             */


}
