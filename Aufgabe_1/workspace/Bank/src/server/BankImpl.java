package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import bank.BankPOA;
import bank.Konto;
import bank.KontoHelper;
import bank.Monitor;
import bank.TKontolisteHolder;
import bank.BankPackage.EKontoAlreadyExists;
import bank.BankPackage.EKontoNotFound;

public class BankImpl extends BankPOA {

    private ArrayList<Monitor> monitorListe = new ArrayList<Monitor>();
    private HashMap<String, Konto> kontoListe = new HashMap<String, Konto>();
    
    private LinkedBlockingQueue<String> sendeQueue = new LinkedBlockingQueue<String>();
   

    Thread sendBank;
    
    public BankImpl() {
        sendBank = new Thread(new SendMessage(this, sendeQueue));
        sendBank.start();
    }

    public synchronized int getKontoliste(TKontolisteHolder kontoliste) {
        int count = 0;
        bank.Konto[] kontoValue = new bank.Konto[this.kontoListe.size()];

        for (String key : kontoListe.keySet()) {
            kontoValue[count++] = kontoListe.get(key);
            System.out.println(key);
        }
        kontoliste.value = kontoValue;
        Server.sendMessage("Konto Liste");
        return count - 1;
    }

    public synchronized Konto neu(String kontonr) throws EKontoAlreadyExists {
        Konto kontoRef = null;
        if (kontonr == null || kontoListe.containsKey(kontonr)) {
            throw new EKontoAlreadyExists("Konto existiert bereits! :"+kontonr);
        } else {
            org.omg.CORBA.Object ref;
            try {
                ref = Server.getRootpoa().servant_to_reference(new KontoImpl(kontonr));
                kontoRef = KontoHelper.narrow(ref);
                kontoListe.put(kontonr, kontoRef);
            } catch (ServantNotActive | WrongPolicy e) {
                e.printStackTrace();
            }
            sendeQueue.add("Neues Konto: " + kontonr);
//            Server.sendMessage("Neues Konto: " + kontonr);
        }

        return kontoRef;
    }

    public synchronized void loeschen(String kontonr) throws EKontoNotFound {
        if (kontonr == null || !kontoListe.containsKey(kontonr)) {
            throw new EKontoNotFound("Konto nicht gefunden!: " + kontonr);
        } else {
            kontoListe.remove(kontonr);
            sendeQueue.add("Loesche Konto: " + kontonr);
//            Server.sendMessage("Loesche Konto: " + kontonr);
        }
    }

    public synchronized Konto hole(String kontonr) throws EKontoNotFound {
        Konto temp = null;

        if (kontonr == null || !kontoListe.containsKey(kontonr)) {
            throw new EKontoNotFound("Konto nicht gefunden!: " + kontonr);
        } else {
            temp = kontoListe.get(kontonr);
            sendeQueue.add("Hole Konto: " + kontonr + ", Kontostand: " + temp.kontostand());
//            Server.sendMessage("Hole Konto: " + kontonr + ", Kontostand: " + temp.kontostand());
        }

        return temp;
    }

    public synchronized void monitorHinzufuegen(Monitor theMonitor) {
        if (theMonitor != null) {
            if (!monitorListe.contains(theMonitor)) {
                monitorListe.add(theMonitor);
                System.out.println("neuer Monitor");
            }
        }
    }

    public synchronized void monitorEntfernen(Monitor theMonitor) {
        
        if (theMonitor != null) {
            if (monitorListe.contains(theMonitor)) {
                monitorListe.remove(theMonitor);
                System.out.println("Monitor entfernt");
            }
        }
    }

    public synchronized void exit() {

        Server.getThread().setMonitorListe(monitorListe);
        
     new Thread( new Runnable() {
            @Override public void run() {
                System.exit(0);
            }
          } ).start();
//        Server.getThread().start();
    }

    public ArrayList<Monitor> getMonitorListe() {
        return monitorListe;
    }

    public void addSendeQueue(String msg) {
        this.sendeQueue.add(msg);
    }



}
