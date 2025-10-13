import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Movie implements Serializable {
    String title;
    double pricePerTicket;
    boolean[][] seats; // true = booked, false = available

    Movie(String title, double pricePerTicket, int rows, int cols) {
        this.title = title;
        this.pricePerTicket = pricePerTicket;
        this.seats = new boolean[rows][cols];
    }

    void displaySeats() {
        System.out.println("\nSeat Layout (O = available, X = booked):");
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] ? "X " : "O ");
            }
            System.out.println();
        }
    }

    boolean isAvailable(int row, int col) {
        return row >= 0 && row < seats.length && col >= 0 && col < seats[0].length && !seats[row][col];
    }

    boolean isBooked(int row, int col) {
        return row >= 0 && row < seats.length && col >= 0 && col < seats[0].length && seats[row][col];
    }

    void bookSeat(int row, int col) {
        seats[row][col] = true;
    }

    void cancelSeat(int row, int col) {
        seats[row][col] = false;
    }
}

class Booking implements Serializable {
    String customerName;
    String movieTitle;
    List<int[]> bookedSeats;
    double totalPrice;
    String dateTime;
    boolean cancelled;

    Booking(String customerName, Movie movie, List<int[]> bookedSeats) {
        this.customerName = customerName;
        this.movieTitle = movie.title;
        this.bookedSeats = bookedSeats;
        this.totalPrice = bookedSeats.size() * movie.pricePerTicket;
        this.dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        this.cancelled = false;
    }

    void displayBookingSummary() {
        System.out.println("\n🎟️ Booking Summary:");
        System.out.println("Customer: " + customerName);
        System.out.println("Movie: " + movieTitle);
        System.out.print("Seats: ");
        for (int[] seat : bookedSeats) {
            System.out.print("[" + (seat[0] + 1) + "," + (seat[1] + 1) + "] ");
        }
        System.out.println("\nTotal Price: ₹" + totalPrice);
        System.out.println("Date & Time: " + dateTime);
        System.out.println("✅ Booking Confirmed!");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(customerName).append("\n");
        sb.append("Movie: ").append(movieTitle).append("\n");
        sb.append("Seats: ");
        for (int[] seat : bookedSeats) {
            sb.append("[").append(seat[0] + 1).append(",").append(seat[1] + 1).append("] ");
        }
        sb.append("\nTotal Price: ₹").append(totalPrice);
        sb.append("\nDate & Time: ").append(dateTime);
        sb.append("\nStatus: ").append(cancelled ? "❌ Cancelled" : "✅ Confirmed");
        sb.append("\n---------------------------\n");
        return sb.toString();
    }
}

public class MovieTicketBookingSystem {
    private static final String MOVIE_FILE = "movies_data.ser";
    private static final String BOOKING_FILE = "bookings_data.ser";
    private static final String HISTORY_FILE = "booking_history.txt";

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Movie[] movies = loadMovies();
        List<Booking> bookings = loadBookings();

        int choice;
        do {
            System.out.println("\n🎬 MOVIE TICKET BOOKING SYSTEM 🎟️");
            System.out.println("1. View Movies & Book Tickets");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Display Booking History");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> bookMovie(movies, bookings);
                case 2 -> cancelBooking(movies, bookings);
                case 3 -> displayBookingHistory();
                case 4 -> {
                    saveMovies(movies);
                    saveBookings(bookings);
                    sc.close(); // Close scanner to prevent resource leak
                    System.out.println("💾 Data saved. Exiting... Goodbye!");
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 4);
    }

    // ✅ Load or initialize movies
    private static Movie[] loadMovies() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MOVIE_FILE))) {
            return (Movie[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚙️ Initializing new movie data...");
            return new Movie[]{
                    new Movie("Avengers: Endgame", 250, 5, 5),
                    new Movie("Inception", 200, 5, 5),
                    new Movie("Interstellar", 220, 5, 5)
            };
        }
    }

    // ✅ Save movies
    private static void saveMovies(Movie[] movies) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MOVIE_FILE))) {
            oos.writeObject(movies);
        } catch (IOException e) {
            System.out.println("Error saving movie data!");
        }
    }

    // ✅ Load bookings
    private static List<Booking> loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKING_FILE))) {
            return (List<Booking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // ✅ Save bookings
    private static void saveBookings(List<Booking> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKING_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Error saving booking data!");
        }

        // Update human-readable history file
        try (PrintWriter out = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            for (Booking b : bookings) {
                out.print(b.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing booking history file!");
        }
    }

    // 🎟️ Booking flow
    private static void bookMovie(Movie[] movies, List<Booking> bookings) {
        System.out.println("\nAvailable Movies:");
        for (int i = 0; i < movies.length; i++) {
            System.out.println((i + 1) + ". " + movies[i].title + " (₹" + movies[i].pricePerTicket + ")");
        }

        System.out.print("\nEnter movie number: ");
        int choice = getIntInput() - 1;

        if (choice < 0 || choice >= movies.length) {
            System.out.println("Invalid movie choice!");
            return;
        }

        Movie selectedMovie = movies[choice];
        selectedMovie.displaySeats();

        System.out.print("\nEnter your name: ");
        String name = getStringInput();

        System.out.print("Enter number of tickets: ");
        int ticketCount = getIntInput();

        List<int[]> bookedSeats = new ArrayList<>();

        for (int i = 0; i < ticketCount; i++) {
            boolean seatBooked = false;
            while (!seatBooked) {
                System.out.println("\nSelect seat " + (i + 1) + " (row and column between 1–5): ");
                int row = getIntInput() - 1;
                int col = getIntInput() - 1;

                if (selectedMovie.isAvailable(row, col)) {
                    selectedMovie.bookSeat(row, col);
                    bookedSeats.add(new int[]{row, col});
                    System.out.println("Seat [" + (row + 1) + "," + (col + 1) + "] booked!");
                    seatBooked = true;
                } else {
                    System.out.println("❌ Seat unavailable or invalid. Please try again.");
                }
            }
        }

        Booking booking = new Booking(name, selectedMovie, bookedSeats);
        booking.displayBookingSummary();
        bookings.add(booking);
        saveMovies(movies);
        saveBookings(bookings);
        System.out.println("\n📂 Booking saved successfully!");
    }

    // ❌ Cancel booking
    private static void cancelBooking(Movie[] movies, List<Booking> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("\n📭 No existing bookings found.");
            return;
        }

        System.out.print("\nEnter your name for cancellation: ");
        String name = getStringInput();

        List<Booking> userBookings = bookings.stream()
                .filter(b -> b.customerName.equalsIgnoreCase(name) && !b.cancelled)
                .toList();

        if (userBookings.isEmpty()) {
            System.out.println("❌ No active bookings found for " + name);
            return;
        }

        System.out.println("\nYour Active Bookings:");
        for (int i = 0; i < userBookings.size(); i++) {
            System.out.println((i + 1) + ". " + userBookings.get(i).movieTitle + " | Seats: " +
                    userBookings.get(i).bookedSeats.size());
        }

        System.out.print("\nEnter booking number to cancel: ");
        int bookingChoice = getIntInput() - 1;

        if (bookingChoice < 0 || bookingChoice >= userBookings.size()) {
            System.out.println("Invalid booking choice!");
            return;
        }

        Booking booking = userBookings.get(bookingChoice);
        double refund = booking.totalPrice * 0.85; // 85% refund policy
        booking.cancelled = true;

        // free seats
        for (Movie m : movies) {
            if (m.title.equalsIgnoreCase(booking.movieTitle)) {
                for (int[] seat : booking.bookedSeats) { // Use the selected booking
                    m.cancelSeat(seat[0], seat[1]);
                }
            }
        }

        saveMovies(movies);
        saveBookings(bookings);
        System.out.println("\n✅ Booking cancelled successfully!");
        System.out.println("💰 Refund Amount: ₹" + refund);
    }

    // 🧾 Display booking history
    private static void displayBookingHistory() {
        File file = new File(HISTORY_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n📭 No booking history found.");
            return;
        }

        System.out.println("\n📜 Booking History:");
        System.out.println("---------------------------");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            System.out.println("Error reading booking history!");
        }
    }

    // 🔢 Safe integer input
    private static int getIntInput() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) { // Be specific with exception
                sc.nextLine();
                System.out.print("Enter a valid number: ");
            }
        }
    }

    // Helper for clean string input
    private static String getStringInput() {
        sc.nextLine(); // consume leftover newline
        return sc.nextLine();
    }
}
