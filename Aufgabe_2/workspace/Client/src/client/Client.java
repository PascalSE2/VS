package client;

import java.util.Scanner;

import koordinator.Koordinator;
import koordinator.KoordinatorHelper;
import koordinator.KoordinatorPackage.EKoordinatorInUse;
import koordinator.monitor.Monitor;
import koordinator.monitor.MonitorHelper;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class Client {

    static final int max_anz_arguments = 11;
    static final int min_anz_arguments = 7;

    public static void main(String[] args) {
        String operationen[] = { "ende", "liste", "start" };

        if (args.length >= min_anz_arguments) {
            int offset = 0;

            for (int i = 0; i < args.length; i++) {
                for (int j = 0; j < operationen.length; j++) {
                    if (args[i].equalsIgnoreCase(operationen[j])) {
                        offset = i;
                        break;
                    }
                }
            }

            int koordinator_index = offset + 1;
            int monitor_index = offset + 2;

            String koordinatorName = args[koordinator_index];
            String monitorName = args[monitor_index];

            koordinatorName = args[koordinator_index];
            monitorName = args[monitor_index];

            // beispiel eingabe
            // String ggT_ProzessName =
            // "-ORBInitialHost <host> -ORBInitialPort <port> <KoordinatorName> <MonitorName> <IntervallAnz> <IntervallDelay> <TerminierungsPeriode> <ggT>";

            ORB orb;
            NamingContextExt ncRef;
            NameComponent path_koordinator[];
            NameComponent path_monitor[];

            System.out.println("Start Client");
            orb = ORB.init(args, null);

            try {
                // Anfordern der referenz vom Namensdienst
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
                // umwandeln von Cobra referenz in Java referenz
                ncRef = NamingContextExtHelper.narrow(objRef);
                // NameComponent nc = new NameComponent("bank","");
                // NameComponent path[] = {nc};
                // Erstellen einer Namens Komponente.
                path_koordinator = ncRef.to_name(koordinatorName);
                path_monitor = ncRef.to_name(monitorName);
                // Anfordern des Objekts mittels des Namensdienst
                Koordinator koordinatorRef = KoordinatorHelper.narrow(ncRef.resolve(path_koordinator));
                Monitor monitorRef = MonitorHelper.narrow(ncRef.resolve(path_monitor));

                koordinatorRef.setMonitor(monitorRef);

                switch (args[offset]) {
                case "ende":
                    koordinatorRef.exit();
                    break;

                case "start":
                    if (args.length >= max_anz_arguments) {
                        int intervall_anz_index = offset + 3;
                        int intervall_delay_index = offset + 4;
                        int terminierungs_periode_index = offset + 5;
                        int ggt_index = offset + 6;
                        Scanner scan_intervall = new Scanner(args[intervall_anz_index] + "-" + args[intervall_delay_index]);
                        scan_intervall.useDelimiter("-");
                        int min_anz = scan_intervall.nextInt();
                        int max_anz = scan_intervall.nextInt();
                        int min_delay = scan_intervall.nextInt();
                        int max_delay = scan_intervall.nextInt();
                        int terminierungs_periode = Integer.parseInt(args[terminierungs_periode_index]);
                        int ggt = Integer.parseInt(args[ggt_index]);
                        koordinatorRef.startBerechnung(min_anz, max_anz, min_delay, max_delay, terminierungs_periode, ggt);
                        break;
                    }
                case "liste":

                    String[] liste = koordinatorRef.getStarterListe();
                    System.out.println("Starterliste:");
                    for (String it : liste) {
                        System.out.println(it);
                    }
                    break;

                default:
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
            } catch (EKoordinatorInUse e) {
                System.err.println(e.s);
            }
        } else {
            System.err.println("Falsche Eingabe");
        }

    }

}
