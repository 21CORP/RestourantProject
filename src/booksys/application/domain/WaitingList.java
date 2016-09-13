package booksys.application.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import booksys.application.persistency.BookingMapper;

public class WaitingList {
    
  private static WaitingList uniqueInstance;
  private Vector<Reservation> queueList = new Vector<Reservation>();
  
    private WaitingList() {
      BookingMapper bm = BookingMapper.getInstance() ;
      Date today = new Date(Calendar.getInstance().getTimeInMillis()) ;
      Vector allReservations = bm.getBookings(today) ;
      
      for (int i = 0; i < allReservations.size(); i++) {
        Reservation current = (Reservation) allReservations.get(i) ;
        if (current.getTable() == null) {
          queueList.add(current) ;
        }
      }
    }
  
  public static WaitingList getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new WaitingList() ;
    }
    return uniqueInstance ;
  }
  
  public boolean add(Reservation r) {
    if (r.getTable() == null) {
      // Vector.add either returns true or throws an exception
      return queueList.add(r) ;
    }
    
    return false;
  }
  
  public boolean remove(Reservation r) {
    return queueList.remove(r) ;
  }
  
  public Vector<Reservation> getQueue(Date date, Time time) {
    Vector<Reservation> queue = new Vector<Reservation>();
    
    for (int i = 0; i < queueList.size(); i++) {
      Reservation current = queueList.get(i) ;
      Date currentDate = current.getDate() ;
      Time currentTime = current.getTime() ;
      
      if (currentTime == time && currentDate == date) {
        queue.add(current) ;
      }
    }
    return queue;
  }

}