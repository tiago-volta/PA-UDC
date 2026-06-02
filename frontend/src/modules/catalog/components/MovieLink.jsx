import {Link} from 'react-router';

const MovieLink = ({id, title}) => {
    return (
        <Link to={`/catalog/movie-details/${id}`} id={`movie-link-${id}`}>
            {title}
        </Link>
    );
}
export default MovieLink;