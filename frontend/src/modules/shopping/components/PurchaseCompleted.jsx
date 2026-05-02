import {useSelector} from 'react-redux';
import {Navigate} from 'react-router';
import {FormattedMessage} from 'react-intl';
import Alert from 'react-bootstrap/Alert';

import * as selectors from '../selectors';

const PurchaseCompleted = () => {

    const purchaseId = useSelector(selectors.getLastPurchaseId);

    if (purchaseId == null) {
        return <Navigate to="/" replace/>;
    }
    
    return (
        <Alert variant="success">
            <FormattedMessage id="project.shopping.PurchaseCompleted.purchaseCompleted"/>:
            &nbsp;
            <strong id="purchase-completed-id">{purchaseId}</strong>
        </Alert>
    );

}

export default PurchaseCompleted;
