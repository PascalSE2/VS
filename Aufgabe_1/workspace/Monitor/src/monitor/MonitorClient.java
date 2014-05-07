package monitor;

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
import bank.MonitorHelper;

public class MonitorClient {

	private static ORB orb;
	private static Bank bankRef;
	private static bank.Monitor mRef;

	public MonitorClient() {
	}

	public static void main(String[] args) {
		String bankName = "Bank";
//		String bankName = args[4];
		System.out.println("Start Monitor");
		// Initialisierung des ORB
		orb = ORB.init(args, null);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
			    bankRef.monitorEntfernen(mRef);
				MonitorClient.getOrb().shutdown(true);
				System.out.println("Monitor shutdown(ShutdownHook)");

			}
		});

		// Thread zum beenden des ORB zum Shutdown Hook Hinzugefuegt.

		// Erstellen der Servant Objekte
		MonitorImpl monitorRef = new MonitorImpl();

		try {
			// CLIENT
			// Anfordern der referenz vom Namensdienst
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			// umwandeln von Cobra referenz in Java referenz
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			// NameComponent nc = new NameComponent("bank","");
			// NameComponent path[] = {nc};
			// Erstellen einer Namens Komponente.
			NameComponent path[] = ncRef.to_name(bankName);
			// Anfordern des Objekts mittels des Namensdienst
			bankRef = BankHelper.narrow(ncRef.resolve(path));

			// SERVER
			// Anfordern der referenz von RootPOA und Aktivierung.
			POA rootpoa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			// Registrieren der Servant objekte
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(monitorRef);
			// umwandeln von Cobra referenz in Java referenz
			mRef = MonitorHelper.narrow(ref);

			/*
			 * //Anfordern der referenz vom Namensdienst org.omg.CORBA.Object
			 * objref = orb.resolve_initial_references("NameService");
			 * //umwandeln von Cobra referenz in Java referenz NamingContextExt
			 * ncRef = NamingContextExtHelper.narrow(objref); //Erstellen einer
			 * Namens Komponente, die mittels Rebind beim Namensdienst
			 * registriert wird. NameComponent path[] = ncRef.to_name("Bank");
			 * ncRef.rebind(path, mRef); // Kontrolle an ORB, es werden auf
			 * anfragen gewartet. orb.run();
			 */

			// Anmelden bei der Bank
			bankRef.monitorHinzufuegen(mRef);
//			 String scanString = "";
//			 Scanner scan = new Scanner(System.in);
//			 scanString = scan.nextLine();
//			 System.out.println(scanString);
//			 bankRef.monitorEntfernen(mRef);
//			 orb.shutdown(true);
			 orb.run();
			// try {
			// thread.join();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

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

	}

	public static ORB getOrb() {
		return orb;
	}

	public static Bank getBankRef() {
		return bankRef;
	}

	public static bank.Monitor getmRef() {
		return mRef;
	}

}
