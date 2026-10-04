/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication94;

import java.util.*;

class InvalidTrainNumberException extends Exception {
    InvalidTrainNumberException(String msg) {
        super(msg);
    }
}

class SeatsUnavailableException extends Exception {
    SeatsUnavailableException(String msg) {
        super(msg);
    }
}

class InvalidPassengerException extends Exception {
    InvalidPassengerException(String msg) {
        super(msg);
    }
}

class CancellationException extends Exception {
    CancellationException(String msg) {
        super(msg);
    }
}

class InvalidTicketException extends Exception {
    InvalidTicketException(String msg) {
        super(msg);
    }
}

class Train {
    int trainNo;
    String trainName;
    String source;
    String destination;
    int seats;

    Train(int trainNo, String trainName, String source,
          String destination, int seats) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
    }

    void display() {
        System.out.println(trainNo + "  " + trainName + "  "
                + source + " -> " + destination
                + "  Seats: " + seats);
    }
}

class Ticket {
    int ticketNo;
    String passengerName;
    int age;
    int trainNo;
    boolean cancelled;

    Ticket(int ticketNo, String passengerName, int age, int trainNo) {
        this.ticketNo = ticketNo;
        this.passengerName = passengerName;
        this.age = age;
        this.trainNo = trainNo;
        this.cancelled = false;
    }
}

public class RailwayReservation {

    static Scanner sc = new Scanner(System.in);

    static Train[] trains = {
        new Train(101, "Chennai Express", "Chennai", "Madurai", 5),
        new Train(102, "Pandian Express", "Chennai", "Madurai", 3),
        new Train(103, "Vaigai Express", "Chennai", "Coimbatore", 4)
    };

    static ArrayList<Ticket> tickets = new ArrayList<>();
    static int nextTicketNo = 1001;

    // Search train
    static void searchTrain() {
        System.out.print("Enter train number: ");
        int no = sc.nextInt();

        for (Train t : trains) {
            if (t.trainNo == no) {
                t.display();
                return;
            }
        }

        try {
            throw new InvalidTrainNumberException(
                    "Invalid train number!");
        } catch (InvalidTrainNumberException e) {
            System.out.println(e.getMessage());
        }
    }

   
    static void bookTicket() {
        System.out.print("Enter train number: ");
        int no = sc.nextInt();

        Train selected = null;

        for (Train t : trains) {
            if (t.trainNo == no) {
                selected = t;
                break;
            }
        }

        try {
            if (selected == null)
                throw new InvalidTrainNumberException(
                        "Invalid train number!");

            if (selected.seats <= 0)
                throw new SeatsUnavailableException(
                        "No seats available!");

            sc.nextLine();

            System.out.print("Enter passenger name: ");
            String name = sc.nextLine();

            System.out.print("Enter passenger age: ");
            int age = sc.nextInt();

            if (name.isEmpty() || age <= 0 || age > 120)
                throw new InvalidPassengerException(
                        "Invalid passenger details!");

            selected.seats--;

            Ticket ticket = new Ticket(
                    nextTicketNo++, name, age, no);

            tickets.add(ticket);

            System.out.println("Ticket booked successfully!");
            System.out.println("Ticket Number: " + ticket.ticketNo);

        } catch (InvalidTrainNumberException |
                 SeatsUnavailableException |
                 InvalidPassengerException e) {

            System.out.println(e.getMessage());
        }
    }

    
    static void cancelTicket() {
        System.out.print("Enter ticket number: ");
        int ticketNo = sc.nextInt();

        try {
            Ticket found = null;

            for (Ticket t : tickets) {
                if (t.ticketNo == ticketNo) {
                    found = t;
                    break;
                }
            }

            if (found == null)
                throw new InvalidTicketException(
                        "Invalid ticket number!");

            if (found.cancelled)
                throw new CancellationException(
                        "Ticket is already cancelled!");

            // Example rule:
            // Cancellation is allowed only if ticket is not cancelled.
            // A real system would compare the current time with
            // the train departure time.

            found.cancelled = true;

            for (Train t : trains) {
                if (t.trainNo == found.trainNo) {
                    t.seats++;
                    break;
                }
            }

            System.out.println("Ticket cancelled successfully!");

        } catch (InvalidTicketException |
                 CancellationException e) {

            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n--- RAILWAY RESERVATION SYSTEM ---");
            System.out.println("1. Search Train");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    searchTrain();
                    break;

                case 2:
                    bookTicket();
                    break;

                case 3:
                    cancelTicket();
                    break;

                case 4:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);
    }
}