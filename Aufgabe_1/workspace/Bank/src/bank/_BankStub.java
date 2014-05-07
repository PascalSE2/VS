package bank;


/**
* bank/_BankStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. M�rz 2014 16:40 Uhr MEZ
*/

public class _BankStub extends org.omg.CORBA.portable.ObjectImpl implements bank.Bank
{


  //holt die aktuelle Kontoliste, R�ckgabewert soll die Gesamtzahl der Konten angeben
  public int getKontoliste (bank.TKontolisteHolder kontoliste)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getKontoliste", true);
                $in = _invoke ($out);
                int $result = $in.read_long ();
                kontoliste.value = bank.TKontolisteHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getKontoliste (kontoliste        );
            } finally {
                _releaseReply ($in);
            }
  } // getKontoliste

  public bank.Konto neu (String kontonr) throws bank.BankPackage.EKontoAlreadyExists
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("neu", true);
                $out.write_string (kontonr);
                $in = _invoke ($out);
                bank.Konto $result = bank.KontoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:bank/Bank/EKontoAlreadyExists:1.0"))
                    throw bank.BankPackage.EKontoAlreadyExistsHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return neu (kontonr        );
            } finally {
                _releaseReply ($in);
            }
  } // neu

  public void loeschen (String kontonr) throws bank.BankPackage.EKontoNotFound
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("loeschen", true);
                $out.write_string (kontonr);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:bank/Bank/EKontoNotFound:1.0"))
                    throw bank.BankPackage.EKontoNotFoundHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                loeschen (kontonr        );
            } finally {
                _releaseReply ($in);
            }
  } // loeschen

  public bank.Konto hole (String kontonr) throws bank.BankPackage.EKontoNotFound
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("hole", true);
                $out.write_string (kontonr);
                $in = _invoke ($out);
                bank.Konto $result = bank.KontoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:bank/Bank/EKontoNotFound:1.0"))
                    throw bank.BankPackage.EKontoNotFoundHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return hole (kontonr        );
            } finally {
                _releaseReply ($in);
            }
  } // hole

  public void monitorHinzufuegen (bank.Monitor theMonitor)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("monitorHinzufuegen", true);
                bank.MonitorHelper.write ($out, theMonitor);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                monitorHinzufuegen (theMonitor        );
            } finally {
                _releaseReply ($in);
            }
  } // monitorHinzufuegen

  public void monitorEntfernen (bank.Monitor theMonitor)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("monitorEntfernen", true);
                bank.MonitorHelper.write ($out, theMonitor);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                monitorEntfernen (theMonitor        );
            } finally {
                _releaseReply ($in);
            }
  } // monitorEntfernen


  //Dient zum Beenden der Bankanwendung. Sorgt dafuer, dass die Bank und alle registrierten Monitore beendet werden.
  public void exit ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("exit", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                exit (        );
            } finally {
                _releaseReply ($in);
            }
  } // exit

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:bank/Bank:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _BankStub
