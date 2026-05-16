import {useSelector} from 'react-redux';
import {Navigate} from 'react-router';
import {FormattedMessage} from 'react-intl';
import Alert from 'react-bootstrap/Alert';

import * as selectors from '../selectors';

const DeliverTicketsCompleted = () => {

    const purchaseId = useSelector(selectors.getLastDeliveredPurchaseId);

    if (purchaseId == null) {
        return <Navigate to="/" replace/>;
    }

    return (
        <Alert variant="success">
            <FormattedMessage id="project.shopping.DeliverTicketsCompleted.ticketsDelivered"/>:
            &nbsp;
            <strong id="deliver-tickets-completed-id">{purchaseId}</strong>
        </Alert>
    );

};

export default DeliverTicketsCompleted;
