/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package railway.ticket.reservation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

class InvalidTrainNumberException extends Exception {
    InvalidTrainNumberException(String message) {
        super(message);
    }
}

class SeatsUnavailableException extends Exception {
    SeatsUnavailableException(String message) {
        super(message);
    }
}

class InvalidPassengerDetailsException extends Exception {
    InvalidPassengerDetailsException(String message) {
        super(message);
    }
}

class CancellationException extends Exception {
    CancellationException(String message) {
        super(message);
    }
}

class InvalidTicketNumberException extends Exception {
    InvalidTicketNumberException(String message) {
        super(message);
    }
}

class Train {
    int trainNumber;
    String trainName;
    String source;
    String destination;
    int seats;
    LocalDateTime departureTime;

    Train(int trainNumber, String trainName, String source,String destination, int seats, LocalDateTime departureTime) {
          
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.departureTime = departureTime;
    }

    void display() {
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Train Name: " + trainName);
        System.out.println("Route: " + source + " -> " + destination);
        System.out.println("Available Seats: " + seats);
        System.out.println("Departure: " + departureTime);
    }
}

class Ticket {
    int ticketNumber;
    String passengerName;
    int age;
    String gender;
    Train train;

    Ticket(int ticketNumber, String passengerName, int age,String gender, Train train) {
           
        this.ticketNumber = ticketNumber;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
        this.train = train;
    }
}




public class RailwayTicketReservation {
     static Scanner sc = new Scanner(System.in);

    static ArrayList<Train> trains = new ArrayList<>();
    static ArrayList<Ticket> tickets = new ArrayList<>();

    static int nextTicketNumber = 1001;

    static {
        trains.add(new Train(101,
                "Chennai Express",
                "Chennai",
                "Madurai",
                5,
                LocalDateTime.now().plusDays(1).withHour(18).withMinute(0)
        ));
                

        trains.add(new Train(
                102,
                "Pandian Express",
                "Chennai",
                "Madurai",
                3,
                LocalDateTime.now().plusDays(2).withHour(20).withMinute(0)
        ));

        trains.add(new Train(
                103,
                "Vaigai Express",
                "Chennai",
                "Coimbatore",
                4,
                LocalDateTime.now().plusDays(3).withHour(7).withMinute(30)
        ));
    }

    static Train findTrain(int trainNumber)
            throws InvalidTrainNumberException {

        for (Train train : trains) {
            if (train.trainNumber == trainNumber) {
                return train;
            }
        }

        throw new InvalidTrainNumberException(
                "Invalid train number."
        );
    }

    static void searchTrain() {

        System.out.print("Enter train number: ");
        int trainNumber = sc.nextInt();

        try {
            Train train = findTrain(trainNumber);
            train.display();

        } catch (InvalidTrainNumberException e) {
            System.out.println(e.getMessage());
        }
    }

    static void bookTicket() {

        System.out.print("Enter train number: ");
        int trainNumber = sc.nextInt();

        try {
            Train train = findTrain(trainNumber);

            if (train.seats <= 0) {
                throw new SeatsUnavailableException("No seats available.");
                        
                
            }

            sc.nextLine();

            System.out.print("Enter passenger name: ");
            String name = sc.nextLine();

            System.out.print("Enter passenger age: ");
            int age = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter passenger gender: ");
            String gender = sc.nextLine();

            if (name.trim().isEmpty()
                    || age <= 0
                    || age > 120
                    || gender.trim().isEmpty()) {

                throw new InvalidPassengerDetailsException("Invalid passenger details.");
                        
                
            }

            Ticket ticket = new Ticket(
                    nextTicketNumber++,
                    name,
                    age,
                    gender,
                    train
            );

            tickets.add(ticket);
            train.seats--;

            System.out.println("Ticket booked successfully.");
            System.out.println("Ticket Number: "+ ticket.ticketNumber);
                    

        } catch (InvalidTrainNumberException |
                 SeatsUnavailableException |
                 InvalidPassengerDetailsException e) {

            System.out.println(e.getMessage());
        }
    }

    static void cancelTicket() {

        System.out.print("Enter ticket number: ");
        int ticketNumber = sc.nextInt();

        try {
            Ticket ticket = null;

            for (Ticket t : tickets) {
                if (t.ticketNumber == ticketNumber) {
                    ticket = t;
                    break;
                }
            }

            if (ticket == null) {
                throw new InvalidTicketNumberException("Invalid ticket number.");
                        
                
            }

            LocalDateTime cancellationLimit =
                    ticket.train.departureTime.minusHours(2);

            if (LocalDateTime.now().isAfter(cancellationLimit)) {
                throw new CancellationException("Cancellation time has expired.");
                        
                
            }

            ticket.train.seats++;
            tickets.remove(ticket);

            System.out.println("Ticket cancelled successfully.");

        } catch (InvalidTicketNumberException |
                 CancellationException e) {

            System.out.println(e.getMessage());
        }
    }

    static void displayTickets() {

        if (tickets.isEmpty()) {
            System.out.println("No booked tickets.");
            return;
        }

        for (Ticket ticket : tickets) {
            System.out.println("\nTicket Number: "+ ticket.ticketNumber);
                    
            System.out.println("Passenger: "+ ticket.passengerName);
                    
            System.out.println("Age: " + ticket.age);
            System.out.println("Gender: " + ticket.gender);
            System.out.println("Train: "+ ticket.train.trainName);
                    
        }
    }
    
    public static void main(String[] args) {
         int choice;

        do {
            System.out.println("\n===== RAILWAY TICKET RESERVATION =====");
            System.out.println("1. Search Train");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Display Booked Tickets");
            System.out.println("5. Exit");
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
                    displayTickets();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }
    
}
