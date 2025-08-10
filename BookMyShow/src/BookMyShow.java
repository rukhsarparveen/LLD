import java.util.*;

enum City {
    Bangalore,
    Delhi;
}
 class Movie {

    int movieId;
    String movieName;
    int movieDurationInMinutes;

    public Movie(int i, String movieName, int movieDurationInMinutes) {
        this.movieId = i;
        this.movieName = movieName;
        this.movieDurationInMinutes = movieDurationInMinutes;
    }
    //other details like Genere, Language etc.

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieDuration() {
        return movieDurationInMinutes;
    }

    public void setMovieDuration(int movieDuration) {
        this.movieDurationInMinutes = movieDuration;
    }
}
  class MovieFactory {
    private static final Map<String, Movie> movieCache = new HashMap<>();

    public static Movie createMovie(int id, String name, int duration) {
        return movieCache.computeIfAbsent(name, k -> new Movie(id, name, duration));
    }
}

class Theatre{
    int theatreId;

    String address;
    String theatreName;
    City city;
    List<Screen> screen = new ArrayList<>();

    List<Show> shows = new ArrayList<>();

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Screen> getScreen() {
        return screen;
    }

    public void setScreen(List<Screen> screen) {
        this.screen = screen;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
class TheatreFactory {
    public static Theatre createTheatre(int theatreId, String name, City city, List<Show> shows) {
        Theatre theatre = new Theatre();
        theatre.setTheatreId(theatreId);
        theatre.setTheatreName(name);
        theatre.setScreen(createScreens());
        theatre.setCity(city);
        theatre.setShows(shows);
        return theatre;
    }

    private static List<Screen> createScreens() {
        Screen screen = new Screen();
        screen.setScreenId(1);
        screen.setSeats(createSeats());
        return Arrays.asList(screen);
    }

    private static List<Seat> createSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            seats.add(new Seat());
        }
        return seats;
    }
}
class Show{
    int showId;
    Movie movie;
    Screen screen;
    int showStartTime;
    List<Integer> bookedSeatIds = new ArrayList<>();

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public int getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(int showStartTime) {
        this.showStartTime = showStartTime;
    }

    public List<Integer> getBookedSeatIds() {
        return bookedSeatIds;
    }

    public void setBookedSeatIds(List<Integer> bookedSeatIds) {
        this.bookedSeatIds = bookedSeatIds;
    }
}

class Screen{
    int screenId;
    List<Seat> seats = new ArrayList<>();

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}

class Seat{
    int seatId;
    int row ;
    SeatCategory seatCategory;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public SeatCategory getSeatCategory() {
        return seatCategory;
    }

    public void setSeatCategory(SeatCategory seatCategory) {
        this.seatCategory = seatCategory;
    }
}

 enum SeatCategory{
    SILVER,
    GOLD,
    PLATINUM;

}

class MovieController{
    Map<City,List<Movie>> cityVsMovies;
    List<Movie> allMovies;

    public MovieController(){
        cityVsMovies = new HashMap<>();
        allMovies = new ArrayList<>();
    }

    public void addMovie(Movie movie, City city){
        allMovies.add(movie);
        List<Movie> movies = cityVsMovies.getOrDefault(city, new ArrayList<>());
        movies.add(movie);
        cityVsMovies.put(city,movies);
    }

    public Movie getMovieByName(String movieName){
        for(Movie movie:allMovies){
            if(movie.getMovieName().equals(movieName)){
                return movie;
            }
        }
        return null;
    }

    public List<Movie> getMoviesByCity(City city){
        return cityVsMovies.get(city);
    }
}

class TheatreController{
    Map<City, List<Theatre>> cityVsTheatre;
    // Delhi -> {PVR, INOX}
    //Mumbai -> {Carnival, INOX}
    List<Theatre> allTheatre;

    public TheatreController(){
        cityVsTheatre = new HashMap<>();
        allTheatre = new ArrayList<>();
    }

    public void addTheatre(Theatre theatre, City city){
        allTheatre.add(theatre);

        List<Theatre> theatres = cityVsTheatre.getOrDefault(city, new ArrayList<>());
        theatres.add(theatre);
        cityVsTheatre.put(city, theatres);
    }

    public Map<Theatre, List<Show>> getAllShow(Movie movie, City city){
        Map<Theatre, List<Show>> theatreVsShows = new HashMap<>();

        List<Theatre> theatres = cityVsTheatre.get(city);

        for(Theatre theatre: theatres) {
            List<Show> givenMovieShows = new ArrayList<>();
            List<Show> shows = theatre.getShows();

            for (Show show : shows) {
                if (show.getMovie().getMovieId() == movie.getMovieId()) {
                    givenMovieShows.add(show);
                }
            }

            if (!givenMovieShows.isEmpty()) {
                theatreVsShows.put(theatre, givenMovieShows);
            }
        }
        return theatreVsShows;
    }
}

class PaymentService{
    public boolean processPayment(double amount){
        System.out.println("Processing payment of rs "+ amount + "...");
        return true;
    }
}
class BookingService{
    private static BookingService instance; // ✅ Singleton instance

    private final MovieController movieController;
    private final TheatreController theatreController;
    private final Scanner scanner;

    private BookingService() { // ✅ Private constructor
        movieController = new MovieController();
        theatreController = new TheatreController();
        scanner = new Scanner(System.in);
    }

    public static BookingService getInstance() { // ✅ Singleton method
        if (instance == null) {
            instance = new BookingService();
        }
        return instance;
    }

    public void startBookingSession() {
        printHeader("🎬 Welcome to BookMyShow 🎟️");
        boolean continueBooking = true;

        while (continueBooking) {
            City userCity = selectCity();
            Movie selectedMovie = selectMovie(userCity);
            Show selectedShow = selectShow(userCity, selectedMovie);
            bookSeat(selectedShow);

            System.out.print("Do you want to book another ticket? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            continueBooking = response.equals("yes");
        }

        printSuccess("Thank you for using BookMyShow! 🎬 Have a great day!");
    }

    private City selectCity() {
        printSection("🏙️ Select Your City");
        for (City city : City.values()) {
            System.out.println("   " + (city.ordinal() + 1) + ". " + city);
        }
        return City.values()[getUserChoice(1, City.values().length) - 1];
    }

    private Movie selectMovie(City city) {
        List<Movie> movies = movieController.getMoviesByCity(city);
        printSection("🎥 Available Movies in " + city);
        for (int i = 0; i < movies.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + movies.get(i).getMovieName());
        }
        return movies.get(getUserChoice(1, movies.size()) - 1);
    }

    private Show selectShow(City city, Movie movie) {
        Map<Theatre, List<Show>> showsMap = theatreController.getAllShow(movie, city);
        // { PVR → [Morning Show, Evening Show], INOX → [Afternoon Show] }

        List<Show> availableShows = new ArrayList<>();
        printSection("🎭 Available Shows for " + movie.getMovieName() + " in " + city);
        int index = 1;
        for (Map.Entry<Theatre, List<Show>> entry : showsMap.entrySet()) {
            for (Show show : entry.getValue()) {
                System.out.println("   " + index + ". " + show.getShowStartTime() + " at 🎦 " + entry.getKey().getTheatreName());
                availableShows.add(show);
                index++;
            }
        }
        return availableShows.get(getUserChoice(1, availableShows.size()) - 1);
    }

    private void bookSeat(Show show) {
        printSection("💺 Select Your Seat (1-100)");
        int seatNumber = getUserChoice(1, 100);
        if (show.getBookedSeatIds().contains(seatNumber)) {
            System.out.println("❌ Seat already booked! Please try another seat.");
            bookSeat(show);
        } else {
            show.getBookedSeatIds().add(seatNumber);
            PaymentService paymentService = new PaymentService();
            boolean paymentSuccess = paymentService.processPayment(250); // Example amount

            if (paymentSuccess) {
                printSuccess("✅ Booking Successful! Enjoy your movie! 🍿");
                generateTicket(show, seatNumber);
            } else {
                System.out.println("❌ Payment failed! Please try again.");
                show.getBookedSeatIds().remove(seatNumber); // Revert seat booking
            }
        }
    }

    private void generateTicket(Show show, int seatNumber) {
        System.out.println("\n========================================");
        System.out.println("🎟️       MOVIE TICKET CONFIRMATION       🎟️");
        System.out.println("========================================");
        System.out.println("🎬 Movie: " + show.getMovie().getMovieName());
        System.out.println("⏰ Show Time: " + show.getShowStartTime() + ":00");
        System.out.println("💺 Seat Number: " + seatNumber);
        System.out.println("----------------------------------------");
        System.out.println("📅 Date: " + java.time.LocalDate.now());
        System.out.println("🆔 Booking ID: " + UUID.randomUUID());
        System.out.println("========================================");
        System.out.println("🎉 Enjoy your movie! 🍿 Have a great time!");
        System.out.println("========================================\n");
    }


    private int getUserChoice(int min, int max) {
        int choice;
        do {
            System.out.print("👉 Enter choice (" + min + "-" + max + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < min || choice > max);
        return choice;
    }

    private void printHeader(String text) {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("          " + text);
        System.out.println("══════════════════════════════════════════\n");
    }

    private void printSection(String text) {
        System.out.println("\n🔹 " + text);
        System.out.println("──────────────────────────────────────────");
    }

    private void printSuccess(String text) {
        System.out.println("\n🎉 " + text + "\n");
    }

    public void initialize() {
        BookingDataFactory.createMovies(movieController);
        BookingDataFactory.createTheatres(movieController, theatreController);
    }
}

class BookingDataFactory {

    public static List<Screen> createScreens() {
        List<Screen> screens = new ArrayList<>();
        Screen screen1 = new Screen();
        screen1.setScreenId(1);
        screen1.setSeats(createSeats());
        screens.add(screen1);
        return screens;
    }

    public static Show createShow(int showId, Movie movie, int showStartTime) {
        Show show = new Show();
        show.setShowId(showId);
        show.setMovie(movie);
        show.setShowStartTime(showStartTime);
        return show;
    }

    public static List<Seat> createSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seats.add(seat);
        }
        return seats;
    }

    public static List<Movie> createMovies(MovieController movieController) {
        Movie barbie = MovieFactory.createMovie(1, "BARBIE", 128);
        Movie oppenheimer = MovieFactory.createMovie(2, "OPPENHEIMER", 180);

        movieController.addMovie(barbie, City.Bangalore);
        movieController.addMovie(barbie, City.Delhi);
        movieController.addMovie(oppenheimer, City.Bangalore);
        movieController.addMovie(oppenheimer, City.Delhi);

        return Arrays.asList(barbie, oppenheimer);
    }

    public static void createTheatres(MovieController movieController, TheatreController theatreController) {
        Movie barbie = movieController.getMovieByName("BARBIE");
        Movie oppenheimer = movieController.getMovieByName("OPPENHEIMER");

        Theatre inox = TheatreFactory.createTheatre(
                1, "INOX", City.Bangalore,
                Arrays.asList(
                        createShow(1, barbie, 10),
                        createShow(2, oppenheimer, 18)
                )
        );

        Theatre pvr = TheatreFactory.createTheatre(
                2, "PVR", City.Delhi,
                Arrays.asList(
                        createShow(3, barbie, 14),
                        createShow(4, oppenheimer, 20)
                )
        );

        theatreController.addTheatre(inox, City.Bangalore);
        theatreController.addTheatre(pvr, City.Delhi);
    }

}
public class BookMyShow {
    public static void main(String[] args) {
        BookingService bookingService = BookingService.getInstance();
        bookingService.initialize();
        bookingService.startBookingSession();
    }
}