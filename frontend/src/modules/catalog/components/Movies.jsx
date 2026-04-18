import Table from "react-bootstrap/Table";
import Alert from "react-bootstrap/Alert";
import {FormattedMessage, useIntl} from "react-intl";


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

        <Table striped hover>

            <thead>
                <tr>
                    <th>
                        <FormattedMessage id='project.catalog.Movies.movie'/>
                    </th>
                    <th>
                        <FormattedMessage id='project.catalog.Movies.sessions'/>
                    </th>
                </tr>
            </thead>

            <tbody>
                {movies.map(item => (
                    <tr key={item.movie.id}>
                        <td>{item.movie.title}</td>
                        <td>
                            {item.sessions.map(session => (
                                <span key={session.id} className="me-2">
                                    {intl.formatDate(new Date(session.date), {
                                        hour: '2-digit',
                                        minute: '2-digit'
                                    })}
                                </span>
                            ))}
                        </td>
                    </tr>
                ))}
            </tbody>

        </Table>

    );
}

export default Movies;