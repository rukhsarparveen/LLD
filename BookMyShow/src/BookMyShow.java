import java.util.*;

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

public enum SeatCategory{
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
            List<Shows> shows = theatre.getShows();

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
class BookingService{
    private static BookingService instance; // singleton instance

    private final MovieController movieController;
    private final TheatreController theatreController;
    private final Scanner scanner;

    private BookingService(){
        movieController = new MovieController();
        theatreController = new TheatreController();
        scanner = new Scanner(System.in);
    }

    public static BookingService getInstance(){
        if(instance == null){
            instance = new BookingService();
        }
        return instance;
    }

    public void startBookingSession(){
        printHeader("Wecome to BookMyShow ");
        boolean continueBooking = true;

        while (continueBooking){
            City userCity = selectCity();
            Movie selectedMovie= selectMovie(userCity);
            Show selectedShow = selectShow(userCity, selectedMovie);
            bookSeat(selectedShow);

            System.out.println("Do you want to book another ticket? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            continueBooking = response.equals("yes");
        }

        printSuccess("Thank you for using BookMyShow");
    }

    private City selectCity(){
        printSection("Select Your City");
        for(City city: City.values()){
            System.out.println(" " + (city.ordinal() + 1)+ ". "+city);
        }

        return City.values()[getUserChoice(1, City.values().length)]
    }

    private Movie selectMovie(City city){
        List<Movie> movies = movieController.getMoviesByCity(city);
        printSection("Available Movies in" + city);
        for(int i=0;i<movies.size();i++){
            System.out.println("  "+(i+1)+". "+movies.get(1).getMovieName());
        }
        return movies.get(getUserChoice(1,movies.size())-1);
    }
}
public class BookMyShow {
    public static void main(String[] args) {
        BookingService bookingService = BookingService.getInstance();
        bookingService.initiali ze();
        bookingService.startBookingSession();
    }
}