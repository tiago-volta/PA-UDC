import {useState, useEffect} from 'react';
import {useParams} from 'react-router-dom';
import backend from '../../../backend';
import BackLink from '../../common/components/BackLink';

const MovieDetails = () => {

    const [movie, setMovie] = useState(null);
    const {id} = useParams();
    const movieId = Number(id); //

    useEffect(() => {
        const findMovieById = async movieId => {
            if (!Number.isNaN(movieId)) {
                const response = await
                    backend.catalogService.findMovieById(movieId);
                if (response.ok) {
                    setMovie(response.payload);
                }
            }
        };

        findMovieById(movieId);

    }, [movieId]);

    //Si la respuesta del backend todavía no llegó
    if (!movie) {
        return null; //No se renderiza nada
    }
    /*El título de la película
    llevará asociado un enlace que permitirá ver la información detallada de la
    película (título, resumen, duración y un enlace para volver a la pantalla anterior).*/
    return (
        <div>
            <BackLink />
            <h2 id="movie-details-title">{movie.title}</h2>
            <p id="movie-details-summary"><strong>Sinopsis:</strong> {movie.summary}</p>
            <p id="movie-details-runtime"><strong>Duración:</strong> {movie.runtime} minutos</p>
        </div>
    );
}
export default MovieDetails;

