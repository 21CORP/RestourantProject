makeQueuedReservation():
  It is basically a wrapper for makeReservation() where Table t = null
  BookingMapper.createReservation() uses Table.getID(), so I gave it value -1 when Table = null (database expects Integer)
  UI doesn't like (Table t = null)
  

There's no UI implementation
  
