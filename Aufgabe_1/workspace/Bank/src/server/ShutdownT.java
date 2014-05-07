package server;

import java.util.ArrayList;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import bank.Monitor;



public class ShutdownT extends Thread {

    private ArrayList<Monitor> monitorListe;
    
	public ShutdownT() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
	      
        for (Monitor it : monitorListe) {  
            it.exit();
        }  
        
        monitorListe.clear();
        
        try {
            Server.getNcRef().unbind(Server.getPath());
        } catch (NotFound e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (CannotProceed e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InvalidName e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
            
		Server.getOrb().shutdown(true);
		System.out.println("Server shutdown");
	}

    public void setMonitorListe(ArrayList<Monitor> monitorListe) {
        this.monitorListe = monitorListe;
    }

}
