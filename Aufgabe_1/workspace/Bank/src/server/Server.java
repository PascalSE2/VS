package server;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import bank.Bank;
import bank.BankHelper;

public class Server {

    private static POA rootpoa;
    private static BankImpl bankRef;
    private static ORB orb;
    private static ShutdownT thread = new ShutdownT();
    private static NameComponent path[];
    private static NamingContextExt ncRef;

    public Server() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        String bankName = "Bank";
//        String bankName = args[4];

        System.out.println("Start Server");
        // Initialisierung des ORB
        orb = ORB.init(args, null);

        // Thread zum beenden des ORB zum Shutdown Hook Hinzugefuegt.
        Runtime.getRuntime().addShutdownHook(thread);

        // Erstellen der Servant Objekte
        bankRef = new BankImpl();        
        
//         KontoImpl kontoRef = new KontoImpl();

        try {
            // Anfordern der referenz von RootPOA und Aktivierung.
            rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            // Registrieren der Servant objekte
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(bankRef);
            // umwandeln von Cobra referenz in Java referenz
            Bank bRef = BankHelper.narrow(ref);
            // Anfordern der referenz vom Namensdienst
            org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");
            // umwandeln von Cobra referenz in Java referenz
            ncRef = NamingContextExtHelper.narrow(objref);
            // Erstellen einer Namens Komponente, die mittels Rebind beim
            // Namensdienst registriert wird.
            path = ncRef.to_name(bankName);
            ncRef.rebind(path, bRef);

            // Kontrolle an ORB, es werden auf anfragen gewartet.
            orb.run();
            // thread.join();

        } catch (InvalidName e) {
            e.printStackTrace();
        } catch (AdapterInactive e) {
            e.printStackTrace();
        } catch (ServantNotActive e) {
            e.printStackTrace();
        } catch (WrongPolicy e) {
            e.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotFound e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CannotProceed e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static POA getRootpoa() {
        return rootpoa;
    }

    public static void sendMessage(String message) {
    }

    public static ORB getOrb() {
        return orb;
    }

    public static ShutdownT getThread() {
        return thread;
    }

    public static NameComponent[] getPath() {
        return path;
    }

    public static NamingContextExt getNcRef() {
        return ncRef;
    }

    public static BankImpl getBankRef() {
        return bankRef;
    }
}
