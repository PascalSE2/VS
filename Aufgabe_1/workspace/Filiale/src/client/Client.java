package client;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import bank.Bank;
import bank.BankHelper;
import bank.Konto;
import bank.TKontolisteHolder;
import bank.BankPackage.EKontoAlreadyExists;
import bank.BankPackage.EKontoNotFound;
import bank.KontoPackage.EInvalidAmount;
import bank.KontoPackage.ENotEnoughMoney;

public class Client {

    public Client() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        int offset = 0;
        String operationen[] = {"ende","liste","anlegen","loeschen","einzahlen","auszahlen","ueberweisen","test"};
       
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < operationen.length; j++) {
                if (args[i].equals(operationen[j])) {
                    offset =  i;
                    break;
                } 
            }
        }
        
        System.out.println("Start Client");
        ORB orb = ORB.init(args, null);
        String kontonr = (args.length >= offset + 3) ? args[offset + 2] : null;
        String tokontonr = (args.length >= offset + 5) ? args[offset + 4] : null;
        String betrag_s = (args.length >= offset + 6) ? args[offset + 5] : (args.length >= offset + 4) ? args[offset + 3] : null;
        double betrag = (betrag_s != null) ? Double.parseDouble(betrag_s) : 0.0;
        TKontolisteHolder kontoliste = new TKontolisteHolder();

        try {
            // Anfordern der referenz vom Namensdienst
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            // umwandeln von Cobra referenz in Java referenz
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            // NameComponent nc = new NameComponent("bank","");
            // NameComponent path[] = {nc};
            // Erstellen einer Namens Komponente.
            NameComponent path[] = ncRef.to_name(args[offset + 1]);
            // Anfordern des Objekts mittels des Namensdienst
            Bank bankRef = BankHelper.narrow(ncRef.resolve(path));

            switch (args[offset]) {
            case "ende":
                bankRef.exit();
                break;
            case "liste":
                int anz = bankRef.getKontoliste(kontoliste);
                for (int i = 0; i < kontoliste.value.length; i++) {
                    System.out.println("Konto: " + kontoliste.value[i].kontonr() + ", Kontostand: " + kontoliste.value[i].kontostand());
                }
                System.out.println("Anzahl der Konten: " + anz);
                break;
            case "anlegen":

                if (kontonr != null) {
                    bankRef.neu(kontonr);
                } else {
                    System.out.println("Keine Kontonr angegeben!");
                }
                break;
            case "loeschen":
                if (kontonr != null) {
                } else {
                    System.out.println("Keine Kontonr angegeben!");
                }
                bankRef.loeschen(kontonr);
                break;
            case "einzahlen":
                if (kontonr != null && betrag != 0) {
                    (bankRef.hole(kontonr)).einzahlen(betrag);
                } else {
                    System.out.println("Fehlende Kontonr oder Fehlender Betrag!");
                }
                break;
            case "auszahlen":
                if (kontonr != null && betrag != 0) {
                    bankRef.hole(kontonr).auszahlen(betrag);
                } else {
                    System.out.println("Fehlende Kontonr oder Fehlender Betrag!");
                }
                break;
            case "ueberweisen":
                if (kontonr != null && tokontonr != null && betrag != 0) {
                    // Erstellen einer Namens Komponente.
                    NameComponent topath[] = ncRef.to_name(args[offset + 3]);
                    // Anfordern des Objekts mittels des Namensdienst
                    Bank toBankRef = BankHelper.narrow(ncRef.resolve(topath));
                    bankRef.hole(kontonr).auszahlen(betrag);
                    toBankRef.hole(tokontonr).einzahlen(betrag);

                } else {
                    System.out.println("Fehlende Kontonr oder Fehlender Betrag!");
                }
                break;
            case "test":
                int n = Integer.parseInt(args[offset + 4]);
                Konto testref = bankRef.hole(kontonr);
                for (int i = 0; i < n; i++) {
                    if (kontonr != null && betrag != 0) {
                    	testref.einzahlen(betrag);
                    } else {
                        System.out.println("Fehlende Kontonr oder Fehlender Betrag!");
                    }
                }

                break;
            default:
                System.out.println("Falsche Eingabe");
                ncRef.unbind(path);
                break;
            }

        } catch (InvalidName e) {
            e.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            e.printStackTrace();
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (EKontoNotFound e) {
            System.err.println(e.s);
        } catch (EInvalidAmount e) {
            System.err.println(e.s);
        } catch (EKontoAlreadyExists e) {
            System.err.println(e.s);            
        } catch (ENotEnoughMoney e) {
            System.err.println(e.s);
        }

    }

}
