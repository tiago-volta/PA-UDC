import {useState, useEffect} from 'react';
import {useParams} from 'react-router';
import {FormattedMessage, FormattedNumber, FormattedDate, FormattedTime} from 'react-intl';
import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import backend from '../../../backend';
import BackLink from '../../common/components/BackLink';
import MovieLink from './MovieLink';
import users from '../../users';
import {useSelector} from 'react-redux';
import {BuyForm} from '../../shopping';
import {Errors} from "../../common/index.js";

const SessionDetails = () => {
    const [session, setSession] = useState(null);
    const [backendErrors, setBackendErrors] = useState(null); //Errores del backend
    const {id} = useParams();
    const sessionId = Number(id);
    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const userRole = useSelector(users.selectors.getUserRole);
    const canBuyTickets = loggedIn && userRole === 'SPECTATOR' && session?.freeSeats > 0;

    useEffect(() => {
        const findSessionById = async sessionId => {
            if (!Number.isNaN(sessionId)) {
                const response = await
                    backend.catalogService.findSessionById(sessionId);
                if (response.ok) {
                    setSession(response.payload);
                }
                else{
                    setBackendErrors(response.payload);
                }
            }
        };

        findSessionById(sessionId);

    }, [sessionId]);

    if (!session) {
        return (
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
        );
    }

    return (
        <div>
            <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>
            <BackLink />
            <Card className="mt-3 shadow-sm mx-auto" style={{maxWidth: '44rem'}}>
                <Card.Body className="p-4">
                    <Card.Title id="session-details-title" className="fs-4 text-center mb-2">
                        <MovieLink id={session.movieId} title={session.movieTitle}/>
                    </Card.Title>
                    <Card.Subtitle id="session-details-date" className="text-muted mb-4 text-center">
                        <FormattedDate value={new Date(session.date)}/> -{' '}
                        <span id="session-details-time">
                            <FormattedTime value={new Date(session.date)}
                                           hour="2-digit"
                                           minute="2-digit"/>
                        </span>
                    </Card.Subtitle>
                        
                    <Row className="mb-3">
                        <Col md={3} className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.runtime'/>
                        </Col>
                        <Col md={6} id="session-details-runtime">
                            {session.movieRuntime}{' '}
                            <FormattedMessage id='project.catalog.MovieDetails.minutes'/>
                        </Col>
                    </Row>
                    <Row className="mb-3">
                        <Col md={3} className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.price'/>
                        </Col>
                        <Col md={6} id="session-details-price">
                            <FormattedNumber value={session.price} style="currency" currency="EUR"/>
                        </Col>
                    </Row>
                    <Row className="mb-3">
                        <Col md={3} className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.room'/>
                        </Col>
                        <Col md={6} id="session-details-room">
                            {session.roomName}
                        </Col>
                    </Row>
                    <Row className="mb-3">
                        <Col md={3} className="text-muted fw-semibold">
                            <FormattedMessage id='project.catalog.SessionDetails.freeSeats'/>
                        </Col>
                        <Col md={6} id="session-details-free-seats">
                            {session.freeSeats}
                        </Col>
                    </Row>

                    {canBuyTickets &&
                        <BuyForm sessionId={session.id} />
                    }
                </Card.Body>
            </Card>
        </div>
    );
}
export default SessionDetails;
