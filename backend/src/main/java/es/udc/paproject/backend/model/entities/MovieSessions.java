package es.udc.paproject.backend.model.entities;
import java.util.List;


//Devolver 1 película y la lista de sesiones asociadas a esa película
public class MovieSessions {
    private final Movie movie;
    private final List<Session> sessions;

    public MovieSessions(Movie movie, List<Session> sessions){
        this.movie=movie;
        this.sessions= sessions;

    }

    public Movie getMovie(){
        return movie;
    }
    public List<Session> getSessions(){
        return sessions;
    }
}
