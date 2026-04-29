import {useState, useEffect} from 'react';
import {useParams} from 'react-router';
import {FormattedMessage, FormattedNumber, FormattedDate, FormattedTime} from 'react-intl';
import Card from 'react-bootstrap/Card';
import backend from '../../../backend';
import BackLink from '../../common/components/BackLink';
import MovieLink from './MovieLink';

const SessionDetails = () => {
    const [session, setSession] = useState(null);
    const {id} = useParams();
    const sessionId = Number(id);

    useEffect(() => {
        const findSessionById = async sessionId => {
            if (!Number.isNaN(sessionId)) {
                const response = await
                    backend.catalogService.findSessionById(sessionId);
                if (response.ok) {
                    setSession(response.payload);
                }
            }
        };

        findSessionById(sessionId);

    }, [sessionId]);

    //Si la respuesta del backend todavía no llegó
    if (!session) {
        return null; //No se renderiza nada
    }
    return (
        <div>
            <BackLink />
            <Card className="mt-3 shadow-sm mx-auto" style={{maxWidth: '44rem'}}>
                <Card.Body className="p-4">
                    <Card.Title id="session-details-title" className="fs-4 text-center">
                        <MovieLink id={session.movieId} title={session.movieTitle}/>
                    </Card.Title>
                    <Card.Subtitle id="session-details-date" className="text-muted mb-4 text-center">
                        <FormattedDate value={new Date(session.date)}/> - <FormattedTime value={new Date(session.date)}/>
                    </Card.Subtitle>
                    <Card.Text id="session-details-runtime">
                        <span className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.runtime'/>
                        </span>{' '}
                        {session.movieRuntime}{' '}
                        <FormattedMessage id='project.catalog.MovieDetails.minutes'/>
                    </Card.Text>
                    <Card.Text id="session-details-price">
                        <span className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.price'/>
                        </span>{' '}
                        <FormattedNumber value={session.price} style="currency" currency="EUR"/>
                    </Card.Text>
                    <Card.Text id="session-details-room">
                        <span className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.room'/>
                        </span>{' '}
                        {session.roomName}
                    </Card.Text>
                    <Card.Text id="session-details-free-seats">
                        <span className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.freeSeats'/>
                        </span>{' '}
                        {session.freeSeats}
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>
    );
}
export default SessionDetails;