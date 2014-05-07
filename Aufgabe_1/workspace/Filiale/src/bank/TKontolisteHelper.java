package bank;


/**
* bank/TKontolisteHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. M�rz 2014 16:29 Uhr MEZ
*/

abstract public class TKontolisteHelper
{
  private static String  _id = "IDL:bank/TKontoliste:1.0";

  public static void insert (org.omg.CORBA.Any a, bank.Konto[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static bank.Konto[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = bank.KontoHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (bank.TKontolisteHelper.id (), "TKontoliste", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static bank.Konto[] read (org.omg.CORBA.portable.InputStream istream)
  {
    bank.Konto value[] = null;
    int _len0 = istream.read_long ();
    value = new bank.Konto[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = bank.KontoHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, bank.Konto[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      bank.KontoHelper.write (ostream, value[_i0]);
  }

}
