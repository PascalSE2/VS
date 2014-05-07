package bank;


/**
* bank/BankOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. M�rz 2014 09:37 Uhr MEZ
*/

public interface BankOperations 
{

  //holt die aktuelle Kontoliste, R�ckgabewert soll die Gesamtzahl der Konten angeben
  int getKontoliste (bank.TKontolisteHolder kontoliste);
  bank.Konto neu (String kontonr) throws bank.BankPackage.EKontoAlreadyExists;
  void loeschen (String kontonr) throws bank.BankPackage.EKontoNotFound;
  bank.Konto hole (String kontonr) throws bank.BankPackage.EKontoNotFound;
  void monitorHinzufuegen (bank.Monitor theMonitor);
  void monitorEntfernen (bank.Monitor theMonitor);

  //Dient zum Beenden der Bankanwendung. Sorgt dafuer, dass die Bank und alle registrierten Monitore beendet werden.
  void exit ();
} // interface BankOperations
