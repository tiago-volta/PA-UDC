import {useState, useEffect} from 'react';
import {useParams} from 'react-router';
import {FormattedMessage} from 'react-intl';
import Card from 'react-bootstrap/Card';
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
            <Card className="mt-3 shadow-sm mx-auto" style={{maxWidth: '44rem'}}>
                <Card.Body className="p-4">
                    <Card.Title id="movie-details-title" className="fs-4 text-center mb-4">
                        {movie.title}
                    </Card.Title>
                    <Card.Text id="movie-details-summary">
                        <span className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.MovieDetails.summary'/>
                        </span>{' '}
                        {movie.summary}
                    </Card.Text>
                    <Card.Text id="movie-details-runtime">
                        <span className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.MovieDetails.runtime'/>
                        </span>{' '}
                        {movie.runtime}{' '}
                        <FormattedMessage id='project.catalog.MovieDetails.minutes'/>
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>
    );
}
export default MovieDetails;

