/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.* ;

public class BookingSystem
{
  // Attributes:

  Date currentDate ;
  Date today ;
  
  // Associations:

  Restaurant restaurant = null ;
  Vector<Booking> currentBookings ;
  Booking selectedBooking ;

  // Singleton:
  
  private static BookingSystem uniqueInstance ;

  public static BookingSystem getInstance()
  {
    if (uniqueInstance == null) {
      uniqueInstance = new BookingSystem() ;
    }
    return uniqueInstance ;
  }

  private BookingSystem()
  {
    today = new Date(Calendar.getInstance().getTimeInMillis()) ;
    restaurant = new Restaurant() ;
  }

  // Observer: this is `Subject/ConcreteSubject'

  Vector<BookingObserver> observers = new Vector<BookingObserver>() ;

  public void addObserver(BookingObserver o)
  {
    observers.addElement(o) ;
  }
  
  public void notifyObservers()
  {
    Enumeration<BookingObserver> enumeration = observers.elements() ;
    while (enumeration.hasMoreElements()) {
      BookingObserver bo = (BookingObserver) enumeration.nextElement() ;
      bo.update() ;
    }
  }

  public boolean observerMessage(String message, boolean confirm)
  {
    BookingObserver bo = (BookingObserver) observers.elementAt(0) ;
    return bo.message(message, confirm) ;
  }
  
  // System messages:

  public void display(Date date)
  {
    currentDate = date ;
    currentBookings = restaurant.getBookings(currentDate) ;
    selectedBooking = null ;
    notifyObservers() ;
  }
  
  public void makeReservation(int covers, Date date, Time time, int tno,
			      String name, String phone)
  {
    if (!overflow(tno, covers)) {
      Booking b;
      if (doubleBooked(time, tno, null)) {
        if (confirmDoubleBook()) {
          b = restaurant.makeQueuedReservation(covers, date, time, name, phone) ;
        } else { return ; }
      } else {
        b = restaurant.makeReservation(covers, date, time, tno, name, phone) ;
      }
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
  }
 
  public void makeWalkIn(int covers, Date date, Time time, int tno)
  {
    if (!doubleBooked(time, tno, null) && !overflow(tno, covers)) {
      Booking b = restaurant.makeWalkIn(covers, date, time, tno) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
  }
  
  public void selectBooking(int tno, Time time)
  {
    selectedBooking = null ;
    Enumeration<Booking> enumeration = currentBookings.elements() ;
    while (enumeration.hasMoreElements()) {
      Booking b = (Booking) enumeration.nextElement() ;
      if (b.getTableNumber() == tno) {
	if (b.getTime().before(time)
	    && b.getEndTime().after(time)) {
	  selectedBooking = b ;
	}
      }
    }
    notifyObservers() ;
  }

  public void cancel()
  {
    if (selectedBooking != null) {
      if (observerMessage("Are you sure?", true)) {
        Vector queueForSlot = restaurant.getQueue(selectedBooking.getDate(), selectedBooking.getTime()) ;
      	currentBookings.remove(selectedBooking) ;
      	restaurant.removeBooking(selectedBooking) ;
      	selectedBooking = null ;
      	
      	if (!queueForSlot.isEmpty()) {
      	  observerMessage(queueForSlot.toString(), true) ;
      	}
      	
      	notifyObservers() ;
      }
    }
  }
  
  public void recordArrival(Time time)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getArrivalTime() != null) {
	observerMessage("Arrival already recorded", false) ;
      }
      else {
	selectedBooking.setArrivalTime(time) ;
	restaurant.updateBooking(selectedBooking) ;
	notifyObservers() ;
      }
    }
  }

  public boolean transfer(Time time, int tno)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getTableNumber() != tno) {
	if (!doubleBooked(selectedBooking.getTime(), tno, selectedBooking)
	    && !overflow(tno, selectedBooking.getCovers())) {
	  selectedBooking.setTable(restaurant.getTable(tno)) ;
	  restaurant.updateBooking(selectedBooking) ;
	}
      }
      notifyObservers() ;
      return true ;
    }
    return false ;
  }
  
  public void dequeueReservation(int tno) {
    if (selectedBooking != null && selectedBooking.getTable() == null) {
      try {
        Reservation r = (Reservation) selectedBooking ;
        Time time = r.getTime() ;
        if (transfer(time, tno) ) {
          restaurant.dequeue(r) ;
        }
      } catch (Exception e) {}
    }
  }
  
  private boolean doubleBooked(Time startTime, int tno, Booking ignore)
  {

    Time endTime = (Time) startTime.clone() ;
    endTime.setHours(endTime.getHours() + 2) ;
    
    Enumeration<Booking> enumeration = currentBookings.elements();
    while (enumeration.hasMoreElements()) {
      Booking b = (Booking) enumeration.nextElement() ;
      if (b != ignore && b.getTableNumber() == tno
          && startTime.before(b.getEndTime())
          && endTime.after(b.getTime())) {
        return true;
      }
    }
    return false ;
  }
  
  private boolean confirmDoubleBook() {
    return observerMessage("Double booking! Would you like to go on the waiting list?", true);
  }
  
  private boolean overflow(int tno, int covers)
  {
    boolean overflow = false ;
    Table t = restaurant.getTable(tno) ;
      
    if (t.getPlaces() < covers) {
      overflow = !observerMessage("Ok to overfill table?", true) ;
    }
    
    return overflow ;
  }
  
  // Other Operations:

  public Date getCurrentDate()
  {
    return currentDate ;
  }
  
  public Enumeration<Booking> getBookings()
  {
    return currentBookings.elements() ;
  }

  public Booking getSelectedBooking()
  {
    return selectedBooking ;
  }

  public static Vector<?> getTableNumbers()
  {
    return Restaurant.getTableNumbers() ;
  }
}
