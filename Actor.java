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

    /**
     *
     * @return name of actor
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return gets the movie and character of the actor
     */
    public String getMovieChar() {
        return movieChar;
    }

    /**
     *
     * @param movie the actor's movie
     * @param character character the actor played
     * @return sets the movie and character of actor
     */
    public String setMovieChar(String movie, String character) {
        return movieChar = movie + " as " + character;

    }

    /**
     *
     * @return prints the name, movie, and character the actor played
     */
    public String toString() {
        return (name + " : " + movieChar);
    }

}
