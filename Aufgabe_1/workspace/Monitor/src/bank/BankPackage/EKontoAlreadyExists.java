package bank.BankPackage;


/**
* bank/BankPackage/EKontoAlreadyExists.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Donnerstag, 27. M�rz 2014 08:03 Uhr MEZ
*/

public final class EKontoAlreadyExists extends org.omg.CORBA.UserException
{
  public String s = null;

  public EKontoAlreadyExists ()
  {
    super(EKontoAlreadyExistsHelper.id());
  } // ctor

  public EKontoAlreadyExists (String _s)
  {
    super(EKontoAlreadyExistsHelper.id());
    s = _s;
  } // ctor


  public EKontoAlreadyExists (String $reason, String _s)
  {
    super(EKontoAlreadyExistsHelper.id() + "  " + $reason);
    s = _s;
  } // ctor

} // class EKontoAlreadyExists
