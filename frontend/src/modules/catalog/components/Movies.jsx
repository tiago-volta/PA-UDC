import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Alert from "react-bootstrap/Alert";
import {FormattedMessage, useIntl} from "react-intl";
import MovieLink from "./MovieLink";


const Movies = ({movies}) => {

    const intl = useIntl();

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
                    <Card className="h-100 border-dark">

                        <Card.Header className="bg-light fw-semibold">
                            <MovieLink id={item.movie.id} title={item.movie.title}/>
                        </Card.Header>

                        <Card.Body>
                            <div className="d-flex flex-wrap gap-2">
                                {item.sessions.map(session => (
                                    <span
                                        key={session.id}
                                        className="badge bg-light text-dark border border-secondary rounded-pill px-3 py-2"
                                    >
                                        {intl.formatDate(new Date(session.date), {
                                            hour: '2-digit',
                                            minute: '2-digit'
                                        })}
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