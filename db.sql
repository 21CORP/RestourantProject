USE RESTOURANT;
DROP TABLE IF EXISTS QueuedReservation;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS WalkIn;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS `Table`;
DROP TABLE IF EXISTS Oid;

CREATE TABLE Oid (
       last_id	    INT NOT NULL
) ;

CREATE TABLE `Table` (
       oid	     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       number	     INT NOT NULL,
       places	     INT NOT NULL
) ;

CREATE TABLE Customer (
       oid	    INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       name	    VARCHAR(32) NOT NULL,
       phoneNumber  CHAR(13) NOT NULL
) ;

CREATE TABLE WalkIn (
       oid	    int NOT NULL PRIMARY KEY,
       covers	    int,
       date	    DATE,
       time	    TIME,
       table_id	    int
) ;

CREATE TABLE Reservation (
       oid	    int NOT NULL PRIMARY KEY,
       covers	    int,
       date	    DATE,
       time	    TIME,
       table_id	    int,
       customer_id  int,
       arrivalTime  TIME
) ;

CREATE TABLE QueuedReservation (
       oid	    int NOT NULL PRIMARY KEY,
       covers	    int,
       date	    DATE,
       time	    TIME,
       customer_id  int);

INSERT INTO Oid VALUES (0) ;

INSERT INTO `Table` (number, places) VALUES (1, 2) ;
INSERT INTO `Table` (number, places) VALUES (2, 2) ;
INSERT INTO `Table` (number, places) VALUES (3, 2) ;
INSERT INTO `Table` (number, places) VALUES (4, 2) ;
INSERT INTO `Table` (number, places) VALUES (5, 4) ;
INSERT INTO `Table` (number, places) VALUES (6, 4) ;
INSERT INTO `Table` (number, places) VALUES (7, 4) ;
INSERT INTO `Table` (number, places) VALUES (8, 4) ;
INSERT INTO `Table` (number, places) VALUES (9, 4) ;
INSERT INTO `Table` (number, places) VALUES (10, 4) ;
