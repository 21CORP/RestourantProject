/*
 * 
 */

package booksys.application.persistency ;

import booksys.application.domain.* ;

public class PersistentQueuedReservation
  extends QueuedReservation implements PersistentBooking
{
  private int oid ;

  public PersistentQueuedReservation(int id, int c, java.sql.Date d, java.sql.Time t, Customer cust)
  {
    super(c, d, t, cust) ;
    oid = id ;
  }

  /* public because getId defined in an interface and hence public */
  
  public int getId() {
    return oid ;
  }
}
