package bank.KontoPackage;


/**
* bank/KontoPackage/ENotEnoughMoney.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. M�rz 2014 16:29 Uhr MEZ
*/

public final class ENotEnoughMoney extends org.omg.CORBA.UserException
{
  public String s = null;

  public ENotEnoughMoney ()
  {
    super(ENotEnoughMoneyHelper.id());
  } // ctor

  public ENotEnoughMoney (String _s)
  {
    super(ENotEnoughMoneyHelper.id());
    s = _s;
  } // ctor


  public ENotEnoughMoney (String $reason, String _s)
  {
    super(ENotEnoughMoneyHelper.id() + "  " + $reason);
    s = _s;
  } // ctor

} // class ENotEnoughMoney
