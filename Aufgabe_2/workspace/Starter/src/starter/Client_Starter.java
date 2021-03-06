package starter;

import koordinator.Koordinator;
import koordinator.KoordinatorHelper;
import koordinator.Starter;
import koordinator.StarterHelper;

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

public class Client_Starter {
    
    static final int anz_arguments = 6;
    static final int host_index = 1;
    static final int port_index = 3;
    static final int offset = 4;
    static final int koordinator_index = offset;
    static final int starter_index = offset + 1;

    static private ORB orb;
    static private Koordinator koordinatorRef ;
    static private String starter_name;
    static private StarterImpl starterRef;
    
    public static void main(String[] args) {

        if (args.length >= anz_arguments) {

            String koordinator_name = args[koordinator_index];
            starter_name = args[starter_index];
            String host = args[host_index];
            int port = Integer.parseInt(args[port_index]);
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                                      
//                    try {
                          Client_Starter.getKoordinatorRef().starterAbmeldung(Client_Starter.getStarter_name());

//                    } catch (COMM_FAILURE) {
//                        // TODO: handle exception
//                    }
                    
                    Client_Starter.getStarterRef().destroyAll();
                    Client_Starter.getOrb().shutdown(true);
                    System.out.println("Starter shutdown(ShutdownHook)");
                }
            });
            
            // beispiel eingabe
            // String ggT_ProzessName =
            // "-ORBInitialHost <host> -ORBInitialPort <port> <KoordinatorName> <StarterName>";
            
            POA rootpoa;
            NamingContextExt ncRef;
            NameComponent path[];

            System.out.println("Start Starter");

            // Initialisierung des ORB
            orb = ORB.init(args, null);

            // Erstellen der Servant Objekte
            starterRef = new StarterImpl(koordinator_name, starter_name, host, port);
            try {
                // CLIENT
                // Anfordern der referenz vom Namensdienst
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
                // umwandeln von Cobra referenz in Java referenz
                ncRef = NamingContextExtHelper.narrow(objRef);
                // Erstellen einer Namens Komponente.
                path = ncRef.to_name(koordinator_name);
                // Anfordern des Objekts mittels des Namensdienst
                koordinatorRef = KoordinatorHelper.narrow(ncRef.resolve(path));

                // SERVER
                // Anfordern der referenz von RootPOA und Aktivierung.
                rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                rootpoa.the_POAManager().activate();
                // Registrieren der Servant objekte
                org.omg.CORBA.Object ref = rootpoa.servant_to_reference(starterRef);
                // umwandeln von Cobra referenz in Java referenz
                Starter sRef = StarterHelper.narrow(ref);

                koordinatorRef.starterAnmeldung(starter_name, sRef);

//			 String scanString = "";
//			 Scanner scan = new Scanner(System.in);
//			 scanString = scan.nextLine();
//			 System.out.println(scanString);
//			 orb.shutdown(true);
                orb.run();

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
        }else {
            System.err.println("Falsche Eingabe");
        }
    }

    public static ORB getOrb() {
        return orb;
    }

    public static Koordinator getKoordinatorRef() {
        return koordinatorRef;
    }

    public static StarterImpl getStarterRef() {
        return starterRef;
    }

    public static String getStarter_name() {
        return starter_name;
    }

}
