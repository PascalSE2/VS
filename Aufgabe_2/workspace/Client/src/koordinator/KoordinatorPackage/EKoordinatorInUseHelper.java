package koordinator.KoordinatorPackage;


/**
* koordinator/KoordinatorPackage/EKoordinatorInUseHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Koordinator.idl
* Dienstag, 6. Mai 2014 11:00 Uhr MESZ
*/

abstract public class EKoordinatorInUseHelper
{
  private static String  _id = "IDL:koordinator/Koordinator/EKoordinatorInUse:1.0";

  public static void insert (org.omg.CORBA.Any a, koordinator.KoordinatorPackage.EKoordinatorInUse that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static koordinator.KoordinatorPackage.EKoordinatorInUse extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [1];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "s",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (koordinator.KoordinatorPackage.EKoordinatorInUseHelper.id (), "EKoordinatorInUse", _members0);
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

  public static koordinator.KoordinatorPackage.EKoordinatorInUse read (org.omg.CORBA.portable.InputStream istream)
  {
    koordinator.KoordinatorPackage.EKoordinatorInUse value = new koordinator.KoordinatorPackage.EKoordinatorInUse ();
    // read and discard the repository ID
    istream.read_string ();
    value.s = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, koordinator.KoordinatorPackage.EKoordinatorInUse value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_string (value.s);
  }

}
