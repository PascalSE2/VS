package ggtProzess;

import java.util.Scanner;

import koordinator.Koordinator;
import koordinator.KoordinatorHelper;
import koordinator.ggT_Prozess;
import koordinator.ggT_ProzessHelper;

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

public class Client_ggT {

    static final int anz_arguments = 8;
    static final int offset = 4;
    static final int koordinator_index = offset;
    static final int starter_index = offset + 1;
    static final int id_index = offset + 2;
    static final int min_delay_index = offset + 3;
    static final int max_delay_index = offset + 4;

    static private ORB orb;
    static private Koordinator koordinatorRef;
    static private ggT_ProzessImpl ggtRef;
    static private String name_id;

    public static void main(String[] args) {
        if (args.length >= anz_arguments) {

            // beispiel eingabe
            // String ggT_ProzessName =
            // "-ORBInitialHost <host> -ORBInitialPort <port> <KoordinatorName> <StarterName> <ID> <ggT> ";
            String koordinatorName = args[koordinator_index];
            String starterName = args[starter_index];
            int ggT_ProzessID = Integer.parseInt(args[id_index]);
            int min_delay = Integer.parseInt(args[min_delay_index]);
            int max_delay = Integer.parseInt(args[max_delay_index]);
            name_id = starterName + " " + ggT_ProzessID;

            POA rootpoa;
            NamingContextExt ncRef;
            NameComponent path[];
            System.out.println("Koordinator: "+ koordinatorName);
            System.out.println("Starter: "+ starterName);
            System.out.println("Start ggT_Prozess: " + name_id);

            // Initialisierung des ORB
            orb = ORB.init(args, null);

            Thread th = new Thread() {
                @Override
                public void run() {

                    if (Client_ggT.getGgtRef().getLinker_channel_reader() != null) {
                        Client_ggT.getGgtRef().getLinker_channel_reader().setRunning(false);
                    }

                    if (Client_ggT.getGgtRef().getRechter_channel_reader() != null) {
                        Client_ggT.getGgtRef().getRechter_channel_reader().setRunning(false);
                    }

                    
                    Client_ggT.getKoordinatorRef().ggT_ProzessAbmeldung(Client_ggT.getName_id());


//                    Client_ggT.getOrb().shutdown(true);
                    System.out.println("ggtProzess shutdown(ShutdownHook)");

                }
            };
            Runtime.getRuntime().addShutdownHook(th);

            try {
                // CLIENT
                // Anfordern der referenz vom Namensdienst
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
                // umwandeln von Cobra referenz in Java referenz
                ncRef = NamingContextExtHelper.narrow(objRef);
                // Erstellen einer Namens Komponente.
                path = ncRef.to_name(koordinatorName);
                // Anfordern des Objekts mittels des Namensdienst
                koordinatorRef = KoordinatorHelper.narrow(ncRef.resolve(path));

                // Erstellen der Servant Objekte
                ggtRef = new ggT_ProzessImpl(koordinatorRef, name_id, min_delay, max_delay);

                // SERVER
                // Anfordern der referenz von RootPOA und Aktivierung.
                rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                rootpoa.the_POAManager().activate();
                // Registrieren der Servant objekte
                org.omg.CORBA.Object ref = rootpoa.servant_to_reference(ggtRef);
                // umwandeln von Cobra referenz in Java referenz
                ggT_Prozess gRef = ggT_ProzessHelper.narrow(ref);

                koordinatorRef.ggT_ProzessAnmeldung(name_id, gRef);

                String scanString = "";
                Scanner scan = new Scanner(System.in);
                scanString = scan.nextLine();
                System.out.println(scanString);
//                orb.shutdown(true);
//                orb.run();
//                koordinatorRef.ggT_ProzessAbmeldung(name_id);
                Runtime.getRuntime().removeShutdownHook(th);
                System.exit(0);
            } catch (InvalidName e) {
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
            } catch (AdapterInactive e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ServantNotActive e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (WrongPolicy e) {
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

    public static Koordinator getKoordinatorRef() {
        return koordinatorRef;
    }

    public static ggT_ProzessImpl getGgtRef() {
        return ggtRef;
    }

    public static String getName_id() {
        return name_id;
    }

}
