import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Alert from "react-bootstrap/Alert";
import {FormattedMessage, FormattedTime} from "react-intl";
import MovieLink from "./MovieLink";
import {Link} from 'react-router';


const Movies = ({movies}) => {

    if (!movies) {
        return null;
    }

    if (movies.length === 0) {
        return (
            <Alert variant="info">
                <FormattedMessage id='project.catalog.Movies.noMoviesFound'/>
            </Alert>
        );
    }

    return (

        <Row xs={1} md={2} lg={3} className="g-3">  {/* contenedor de filas: móviles: 1 columna, tablet: 2 columnas, ordenador: 3 columnas */}
            {movies.map(item => (
                <Col key={item.movie.id}>
                    <Card className="h-100 border-dark shadow-sm">

                        <Card.Header className="bg-light fw-semibold">
                            <MovieLink id={item.movie.id} title={item.movie.title}/>
                        </Card.Header>

                        <Card.Body>
                            <div className="d-flex flex-wrap gap-2">
                                {item.sessions.map(session => (
                                    <span
                                        key={session.id}
                                        className="badge bg-light border border-secondary rounded-pill px-3 py-2"
                                    >
                                        <Link to={`/catalog/session-details/${session.id}`}
                                              id={`session-link-${session.id}`}>
                                            <FormattedTime value={new Date(session.date)}
                                                             hour="2-digit"
                                                             minute="2-digit"/>
                                        </Link>
                                    </span>
                                ))}
                            </div>
                        </Card.Body>

                    </Card>
                </Col>
            ))}
        </Row>

    );
}

export default Movies;