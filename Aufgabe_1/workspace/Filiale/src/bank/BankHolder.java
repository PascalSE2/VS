package bank;

/**
* bank/BankHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. M�rz 2014 16:29 Uhr MEZ
*/

public final class BankHolder implements org.omg.CORBA.portable.Streamable
{
  public bank.Bank value = null;

  public BankHolder ()
  {
  }

  public BankHolder (bank.Bank initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = bank.BankHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    bank.BankHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return bank.BankHelper.type ();
  }

}
