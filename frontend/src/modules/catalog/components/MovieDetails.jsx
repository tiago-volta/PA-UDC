import {useState, useEffect} from 'react';
import {useParams} from 'react-router';
import {FormattedMessage} from 'react-intl';
import backend from '../../../backend';
import BackLink from '../../common/components/BackLink';

const MovieDetails = () => {

    const [movie, setMovie] = useState(null);
    const {id} = useParams();
    const movieId = Number(id);

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
            <p id="movie-details-summary">
                <strong>
                    <FormattedMessage id='project.catalog.MovieDetails.summary'/>
                </strong>{' '}
                {movie.summary}
            </p>
            <p id="movie-details-runtime">
                <strong>
                    <FormattedMessage id='project.catalog.MovieDetails.runtime'/>
                </strong>{' '}
                {movie.runtime}{' '}
                <FormattedMessage id='project.catalog.MovieDetails.minutes'/>
            </p>
        </div>
    );
}
export default MovieDetails;

