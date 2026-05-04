import {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import * as actions from '../actions';
import backend from '../../../backend';
import users from '../../users';
import {Errors} from '../../common';


const BuyForm = ({sessionId}) => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [numTickets, setNumTickets] = useState(1);
    const [bankCard, setBankCard] = useState('');
    const [formValidated, setFormValidated] = useState(false);
    const [backendErrors, setBackendErrors] = useState(null);
    const userId = useSelector(users.selectors.getUserId);
    let form;

    const handleSubmit = async event => {

        event.preventDefault();

        if (form.checkValidity()) {

            const response = await backend.catalogService.buyTickets(userId, sessionId, numTickets, bankCard);

            if (response.ok) {
                dispatch(actions.buyCompleted(response.payload));
                navigate('/shopping/purchase-completed');
            } else {
                setBackendErrors(response.payload);
            }

        } else {
            setBackendErrors(null);
            setFormValidated(true);
        }

    }

    return (

        <>
            <div className="border-top pt-4 mt-4">
                <div id="buy-form-title" className="fs-4 text-center mb-4">
                    <FormattedMessage id="project.shopping.BuyForm.title"/>
                </div>
                <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>
                <Form ref={node => form = node}
                    validated={formValidated} noValidate
                    onSubmit={(e) => handleSubmit(e)}>
                    <Form.Group as={Row} className="mb-3 align-items-start" controlId="numTickets">
                        <Form.Label column md={3} className="text-muted fw-semibold mb-0">
                            <FormattedMessage id="project.shopping.BuyForm.numTickets"/>
                        </Form.Label>
                        <Col md={6}>
                            <Form.Control type="number"
                                value={numTickets}
                                onChange={e => setNumTickets(Number(e.target.value))}
                                autoFocus
                                min={1}
                                max={10}/>
                            <Form.Control.Feedback type="invalid">
                                <FormattedMessage id='project.shopping.BuyForm.numTickets.error'/>
                            </Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="mb-3 align-items-start" controlId="bankCard">
                        <Form.Label column md={3} className="text-muted fw-semibold mb-0">
                            <FormattedMessage id="project.shopping.BuyForm.bankCard"/>
                        </Form.Label>
                        <Col md={6}>
                            <Form.Control type="text"
                                value={bankCard}
                                onChange={e => setBankCard(e.target.value)}
                                autoComplete="cc-number"
                                required/>
                            <Form.Control.Feedback type="invalid">
                                <FormattedMessage id='project.global.validator.required'/>
                            </Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="align-items-center">
                        <Col md={{span: 6, offset: 3}}>
                            <Button id="buy-form-submit" type="submit" variant="primary">
                                <FormattedMessage id="project.shopping.BuyForm.submit"/>
                            </Button>
                        </Col>
                    </Form.Group>
                </Form>
            </div>
        </>

    );

}

export default BuyForm;
