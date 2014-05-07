package bank;


/**
* bank/_KontoStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Donnerstag, 27. M�rz 2014 08:03 Uhr MEZ
*/

public class _KontoStub extends org.omg.CORBA.portable.ObjectImpl implements bank.Konto
{

  public double kontostand ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_kontostand", true);
                $in = _invoke ($out);
                double $result = $in.read_double ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return kontostand (        );
            } finally {
                _releaseReply ($in);
            }
  } // kontostand


  //Kontostand
  public String kontonr ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_kontonr", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return kontonr (        );
            } finally {
                _releaseReply ($in);
            }
  } // kontonr


  //Kontonummer
  public void einzahlen (double betrag) throws bank.KontoPackage.EInvalidAmount
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("einzahlen", true);
                $out.write_double (betrag);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:bank/Konto/EInvalidAmount:1.0"))
                    throw bank.KontoPackage.EInvalidAmountHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                einzahlen (betrag        );
            } finally {
                _releaseReply ($in);
            }
  } // einzahlen

  public void auszahlen (double betrag) throws bank.KontoPackage.EInvalidAmount, bank.KontoPackage.ENotEnoughMoney
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("auszahlen", true);
                $out.write_double (betrag);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:bank/Konto/EInvalidAmount:1.0"))
                    throw bank.KontoPackage.EInvalidAmountHelper.read ($in);
                else if (_id.equals ("IDL:bank/Konto/ENotEnoughMoney:1.0"))
                    throw bank.KontoPackage.ENotEnoughMoneyHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                auszahlen (betrag        );
            } finally {
                _releaseReply ($in);
            }
  } // auszahlen

  public void transfer (double betrag, bank.Konto toKonto) throws bank.KontoPackage.EInvalidAmount
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("transfer", true);
                $out.write_double (betrag);
                bank.KontoHelper.write ($out, toKonto);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:bank/Konto/EInvalidAmount:1.0"))
                    throw bank.KontoPackage.EInvalidAmountHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                transfer (betrag, toKonto        );
            } finally {
                _releaseReply ($in);
            }
  } // transfer

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:bank/Konto:1.0"};

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
} // class _KontoStub
