import {useState} from 'react';
import {FormattedMessage, useIntl} from 'react-intl';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';

import backend from '../../../backend';
import {Errors, Success} from '../../common';

const DeliverTickets = () => {

    const [purchaseId, setPurchaseId] = useState('');
    const [bankCard, setBankCard] = useState('');
    const [formValidated, setFormValidated] = useState(false);
    const [backendErrors, setBackendErrors] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const intl = useIntl();
    let form;

    const handleSubmit = async event => {

        event.preventDefault();

        if (form.checkValidity()) {

            const response = await backend.purchaseService.deliverTickets(Number(purchaseId), bankCard);

            if (response.ok) {
                setBackendErrors(null);
                setSuccessMessage(intl.formatMessage({id: 'project.shopping.DeliverTickets.success'}));
            } else if (response.httpStatus === 403) {
                setSuccessMessage(null);
                setBackendErrors({
                    globalError: intl.formatMessage({id: 'project.global.exceptions.Forbidden'})
                });
            } else {
                setSuccessMessage(null);
                setBackendErrors(response.payload);
            }

        } else {
            setSuccessMessage(null);
            setBackendErrors(null);
            setFormValidated(true);
        }

    };

    return (
        <div className="col-md-10 mx-auto">
            <Success message={successMessage}
                onClose={() => setSuccessMessage(null)}/>
            <Errors errors={backendErrors}
                onClose={() => setBackendErrors(null)}/>
            <Card className="bg-light border-dark">
                <Card.Header as="h5">
                    <FormattedMessage id="project.shopping.DeliverTickets.title"/>
                </Card.Header>
                <Card.Body>
                    <Form ref={node => form = node}
                        validated={formValidated} noValidate
                        onSubmit={e => handleSubmit(e)}>
                        <Form.Group as={Row} className="mb-3" controlId="purchaseId">
                            <Form.Label column md={3}>
                                <FormattedMessage id="project.global.fields.purchaseId"/>
                            </Form.Label>
                            <Col md={4}>
                                <Form.Control type="number"
                                    value={purchaseId}
                                    onChange={e => setPurchaseId(e.target.value)}
                                    min={1}
                                    autoFocus
                                    required/>
                                <Form.Control.Feedback type="invalid">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </Form.Control.Feedback>
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row} className="mb-3" controlId="bankCard">
                            <Form.Label column md={3}>
                                <FormattedMessage id="project.global.fields.bankCard"/>
                            </Form.Label>
                            <Col md={4}>
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
                        <Form.Group as={Row}>
                            <Col md={{span: 4, offset: 3}}>
                                <Button type="submit">
                                    <FormattedMessage id="project.shopping.DeliverTickets.submit"/>
                                </Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    );

};

export default DeliverTickets;
