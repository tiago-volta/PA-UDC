import {Link} from 'react-router-dom';

const MovieLink = ({id, title}) => {
    return (
        <Link to={`/catalog/movie-details/${id}`}>
            {/*No estoy 100% de que la ruta sea esta*/}
            {title}
        </Link>
    );
}
export default MovieLink;