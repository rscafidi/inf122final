package gameenvironmentplugin;

public class OccupiedAlready extends RuntimeException
{
   public OccupiedAlready(String msg)
   {
      super(msg);
   }
}