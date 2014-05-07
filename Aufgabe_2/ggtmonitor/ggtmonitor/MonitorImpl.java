/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggtmonitor;

import java.util.ArrayList;

/**
 *
 * @author heitmann
 */
public class MonitorImpl extends monitor.MonitorPOA {

    private ArrayList<String> prozessIds;
    private long t0;
    

    @Override
    public synchronized void ring(String[] prozessIds) {
        t0 = System.currentTimeMillis();
        this.prozessIds = new ArrayList<String>();

        System.out.printf("\n\n\n");
        System.out.printf("+-----------");
        for (String s : prozessIds) {
            System.out.printf("+-----------");
        }
        System.out.printf("+\n");

        System.out.printf("| %10s", "Zeit [ms]");
        for (String s : prozessIds) {
            System.out.printf("| %10s", s);
            this.prozessIds.add(s);
        }
        System.out.printf("|\n");

    }

    @Override
    public void startzahlen(int[] startzahlen) {
        System.out.printf("|           ");
        for (int z : startzahlen) {
            System.out.printf("| %9d ", z);
        }
        System.out.printf("|\n");

        System.out.printf("+-----------");
        for (String s : prozessIds) {
            System.out.printf("+-----------");
        }
        System.out.printf("+\n");
    }

    @Override
    public synchronized void rechnen(String prozessId, String prozessIdAbsender, int num) {
        System.out.printf("| %9d ", System.currentTimeMillis()-t0);
        int ix = prozessIds.indexOf(prozessId);
        int ixAbs = prozessIds.indexOf(prozessIdAbsender);

        boolean vonRechts = false;
        boolean vonLinks = false;
        if (ixAbs >= 0) {
            vonRechts = ixAbs == (ix + 1) % prozessIds.size();
            vonLinks = ix == (ixAbs + 1) % prozessIds.size();
        }
        int i;
        for (i = 0; i < prozessIds.size(); i++) {
            System.out.print("|");

            if (i == ix) {
                if (vonLinks) {
                    System.out.print(">");
                } else {
                    System.out.print(" ");
                }


                System.out.printf(" %8d", num);


                if (vonRechts) {
                    System.out.print("<");
                } else {
                    System.out.print(" ");
                }
            } else {
                System.out.printf(" %10s", " ");
            }
        }
        System.out.printf("|\n");
    }

    @Override
    public synchronized void terminieren(String prozessId, String prozessIdAbsender, boolean terminiere) {
        int ix = prozessIds.indexOf(prozessId);
        System.out.printf("| %9d ", System.currentTimeMillis()-t0);
        for (int i = 0; i < prozessIds.size(); i++) {
            if (i == ix) {
                if (terminiere) {
                    System.out.print("|\033[42m T:");
                } else {
                    System.out.print("|\033[41m T:");
                }
                System.out.printf("%8s\033[47m", prozessIdAbsender);
            } else {
                System.out.printf("| %10s", " ");
            }
        }
        System.out.printf("|\n");
    }

    @Override
    public synchronized void ergebnis(String prozessId, int num) {
        int ix = prozessIds.indexOf(prozessId);
        System.out.printf("| %9d ", System.currentTimeMillis()-t0);
        for (int i = 0; i < prozessIds.size(); i++) {
            if (i == ix) {
                System.out.printf("|\033[43m ### %6d\033[47m", num);
            } else {
                System.out.printf("| %10s", " ");
            }
        }
        System.out.printf("|\n");
    }
}
