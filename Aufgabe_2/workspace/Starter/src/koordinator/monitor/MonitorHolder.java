package koordinator.monitor;

/**
* koordinator/monitor/MonitorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Koordinator.idl
* Dienstag, 6. Mai 2014 11:00 Uhr MESZ
*/

public final class MonitorHolder implements org.omg.CORBA.portable.Streamable
{
  public koordinator.monitor.Monitor value = null;

  public MonitorHolder ()
  {
  }

  public MonitorHolder (koordinator.monitor.Monitor initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = koordinator.monitor.MonitorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    koordinator.monitor.MonitorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return koordinator.monitor.MonitorHelper.type ();
  }

}
