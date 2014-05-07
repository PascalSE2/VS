package koordinator;


/**
* koordinator/NachrichtHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Koordinator.idl
* Dienstag, 6. Mai 2014 11:00 Uhr MESZ
*/

abstract public class NachrichtHelper
{
  private static String  _id = "IDL:koordinator/Nachricht:1.0";

  public static void insert (org.omg.CORBA.Any a, koordinator.Nachricht that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static koordinator.Nachricht extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [5];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = koordinator.NachrichtenTypHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "typ",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "ggt",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "sequenz",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "name_id",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[4] = new org.omg.CORBA.StructMember (
            "terminierung",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (koordinator.NachrichtHelper.id (), "Nachricht", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static koordinator.Nachricht read (org.omg.CORBA.portable.InputStream istream)
  {
    koordinator.Nachricht value = new koordinator.Nachricht ();
    value.typ = koordinator.NachrichtenTypHelper.read (istream);
    value.ggt = istream.read_long ();
    value.sequenz = istream.read_long ();
    value.name_id = istream.read_string ();
    value.terminierung = istream.read_boolean ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, koordinator.Nachricht value)
  {
    koordinator.NachrichtenTypHelper.write (ostream, value.typ);
    ostream.write_long (value.ggt);
    ostream.write_long (value.sequenz);
    ostream.write_string (value.name_id);
    ostream.write_boolean (value.terminierung);
  }

}
