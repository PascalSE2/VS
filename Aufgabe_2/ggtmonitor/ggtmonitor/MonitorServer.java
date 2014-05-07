/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggtmonitor;

import monitor.Monitor;
import monitor.MonitorHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 *
 * @author heitmann
 */
public class MonitorServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // ORB Eigenschaften setzen
            ORB orb = ORB.init(args, null);

            // Referenz von rootPOA holen und POA Manager aktivieren
            POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPoa.the_POAManager().activate();

            // Referenz zum Namensdiesnt (root naming context) holen
            NamingContextExt nameservice = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            Monitor monitor = MonitorHelper.narrow(
                    rootPoa.servant_to_reference(new MonitorImpl()));
            nameservice.rebind(nameservice.to_name(args[0]), monitor);


            System.out.printf("Monitor %s Ready ...\n", args[0] );
            orb.run();

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
