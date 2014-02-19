package business;

public class Video extends Product {

    // Initate class variables
    String rating, principalActors, director, runningTime, format, year;

    // Class consutrctor
    public Video(String ID, String name, String genre, String medium, String rating, String principalActors, String director, String runningTime, String format, String year, String qty, String imageURL) {
        super(ID, name, genre, medium, qty, imageURL);
        this.rating = rating;
        this.principalActors = principalActors;
        this.director = director;
        this.runningTime = runningTime;
        this.format = format;
        this.year = year;
    }

    // Overwrite Java's to string to output with line breaks
    public String toString() {
        String msg = "";
        msg += super.toString();
        msg += "Rating: " + rating + "\n";
        msg += "Principal Actors: " + principalActors + "\n";
        msg += "Director: " + director + "\n";
        msg += "Running Time: " + runningTime + "\n";
        msg += "Format: " + format + "\n";
        msg += "Year: " + year + "\n";

        return msg;
    }
}
