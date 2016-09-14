/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import booksys.application.persistency.* ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.Vector ;

class Restaurant
{
  BookingMapper  bm = BookingMapper.getInstance() ;
  CustomerMapper cm = CustomerMapper.getInstance() ;
  TableMapper    tm = TableMapper.getInstance() ;
  
  Vector getBookings(Date date)
  {
    return bm.getBookings(date) ;
  }

  Customer getCustomer(String name, String phone)
  {
    return cm.getCustomer(name, phone) ;
  }
  
  Table getTable(int n)
  {
    return tm.getTable(n) ;
  }

  static Vector getTableNumbers()
  {
    return TableMapper.getInstance().getTableNumbers() ;
  }

  public Booking makeReservation(int covers, Date date,
				     Time time,
				     int tno, String name, String phone)
  {
    Table t = getTable(tno) ;
    Customer c = getCustomer(name, phone) ;
    return bm.createReservation(covers, date, time, t, c, null) ;
  }

  public Booking makeQueuedReservation(int covers, Date date,
             Time time,
             String name, String phone)
  {
    Customer c = getCustomer(name, phone) ;
    QueuedReservation qr = bm.createQueuedReservation(covers, date, time, c) ;
    
    return qr ;
  }
  

  public Booking makeWalkIn(int covers, Date date,
			   Time time, int tno)
  {
    Table t = getTable(tno) ;
    return bm.createWalkIn(covers, date, time, t) ;
  }

  public void updateBooking(Booking b)
  {
    bm.updateBooking(b) ;
  }
  
  public void removeBooking(Booking b) {
    bm.deleteBooking(b) ;
  }
  
  public void dequeue(QueuedReservation qr, int tno) {
    int cover = qr.getCovers() ;
    Date date = qr.getDate() ;
    Time time = qr.getTime() ;
    String nm = qr.getCustomer().getName() ;
    String ph = qr.getCustomer().getPhoneNumber() ;
    
    Reservation r = (Reservation) makeReservation(cover, date, time, tno, nm, ph) ;
    removeBooking(qr);
  }
  
  public Vector getQueue(Date date, Time time) {
    return bm.getWaitingList(date, time) ;
  }
}
