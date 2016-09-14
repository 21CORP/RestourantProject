/*
 * 
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;

public class QueuedReservation extends BookingImp
{
  private Customer customer ;
  
  public QueuedReservation(int c, Date d, Time t, Customer cust)
  {
    super(c, d, t, null) ;
    customer    = cust ;
  }

  public Customer getCustomer() {
    return customer ;
  }

  public String getDetails()
  {
    StringBuffer details = new StringBuffer(64) ;
    details.append(customer.getName()) ;
    details.append(" ") ;
    details.append(customer.getPhoneNumber()) ;
    details.append(" (") ;
    details.append(covers) ;
    details.append(")") ;
    return details.toString() ;
  }

  public void setCustomer(Customer c) {
    customer = c ;
  }
}
