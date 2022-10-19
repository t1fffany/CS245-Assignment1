import java.util.ArrayList;
public class Actor {
    private String name;
    private String movieChar;
    private ArrayList<String> movies = new ArrayList<String>();

    /**
     *
     * @param name Name of Actor
     * @param movie Name of Movie they were in
     * @param character Name of the character they played in the movie
     */
    public Actor(String name, String movie, String character) {
        this.name = name;
        movieChar = setMovieChar(movie, character);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieChar() {
        return movieChar;
    }

    public String setMovieChar(String movie, String character) {
        return movieChar = movie + " as " + character;

    }

    public String toString() {
        return (name + " : " + movieChar);
    }

}
