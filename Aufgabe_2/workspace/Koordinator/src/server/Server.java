package server;

import java.util.HashMap;

import koordinator.Koordinator;
import koordinator.KoordinatorHelper;
import koordinator.Starter;

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

public class Server {

    static final int anz_arguments = 5;
    static final int offset = 4;
    static final int koordinator_index = offset;

    static private ORB orb;
    static private NamingContextExt ncRef;
    static private NameComponent path[];
    static private KoordinatorImpl koordinatorRef;

    public static void main(String[] args) {

        if (args.length >= anz_arguments) {

            POA rootpoa;
            String koordinatorName = args[koordinator_index];
            // beispiel eingabe
            // "-ORBInitialHost <host> -ORBInitialPort <port> <KoordinatorName>";

            System.out.println("Start Koordinator");

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {

                    HashMap<String, Starter> starter_map = Server.getKoordinatorRef().getStarterMap();
                    
                    for (String it : starter_map.keySet()) {
                        starter_map.get(it).exit();
                    }
                    
                    try {
                        Server.getNcRef().unbind(Server.getPath());
                        Server.getOrb().shutdown(true);
                    } catch (NotFound e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (CannotProceed e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    System.out.println("Koordinator shutdown(ShutdownHook)");

                }
            });

            // Initialisierung des ORB
            orb = ORB.init(args, null);

            // Thread zum beenden des ORB zum Shutdown Hook Hinzugefuegt.
//        Runtime.getRuntime().addShutdownHook(thread);

            // Erstellen der Servant Objekte
            koordinatorRef = new KoordinatorImpl();
            // Anfordern der referenz von RootPOA und Aktivierung.
            try {
                rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                rootpoa.the_POAManager().activate();
                // Registrieren der Servant objekte
                org.omg.CORBA.Object ref = rootpoa.servant_to_reference(koordinatorRef);
                // umwandeln von Cobra referenz in Java referenz
                Koordinator kRef = KoordinatorHelper.narrow(ref);
                // Anfordern der referenz vom Namensdienst
                org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");
                // umwandeln von Cobra referenz in Java referenz
                ncRef = NamingContextExtHelper.narrow(objref);
                // Erstellen einer Namens Komponente, die mittels Rebind beim
                // Namensdienst registriert wird.
                path = ncRef.to_name(koordinatorName);
                ncRef.rebind(path, kRef);
                // Kontrolle an ORB, es werden auf anfragen gewartet.
                orb.run();

            } catch (InvalidName e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (AdapterInactive e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ServantNotActive e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (WrongPolicy e) {
                // TODO Auto-generated catch block
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
        } else {
            System.err.println("Falsche Eingabe");
        }

    }

    public static ORB getOrb() {
        return orb;
    }

    public static NamingContextExt getNcRef() {
        return ncRef;
    }

    public static NameComponent[] getPath() {
        return path;
    }

    public static KoordinatorImpl getKoordinatorRef() {
        return koordinatorRef;
    }
}
