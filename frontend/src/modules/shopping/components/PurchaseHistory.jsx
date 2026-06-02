import {useEffect, useState} from 'react';
import {FormattedDate, FormattedMessage, FormattedNumber, FormattedTime} from 'react-intl';
import Alert from 'react-bootstrap/Alert';
import Badge from 'react-bootstrap/Badge';
import Table from 'react-bootstrap/Table';

import backend from '../../../backend';
import {Errors, Pager} from '../../common';

const PurchaseHistory = () => {

    const [purchaseSearch, setPurchaseSearch] = useState(null);
    const [page, setPage] = useState(0);
    const [backendErrors, setBackendErrors] = useState(null);

    useEffect(() => {

        const findPurchases = async () => {

            const response = await backend.purchaseService.findPurchases(page);

            if (response.ok) {
                setPurchaseSearch(response.payload);
                setBackendErrors(null);
            } else {
                setBackendErrors(response.payload);
            }

        };

        findPurchases();

    }, [page]);

    if (!purchaseSearch) {
        return (
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
        );
    }

    return (
        <div>
            <h3 className="mb-4">
                <FormattedMessage id="project.shopping.PurchaseHistory.title"/>
            </h3>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            {purchaseSearch.items.length === 0 ? (
                <Alert variant="info">
                    <FormattedMessage id="project.shopping.PurchaseHistory.noPurchases"/>
                </Alert>
            ) : (
                <>
                    <Table responsive hover className="align-middle">
                        <thead>
                            <tr>
                                <th><FormattedMessage id="project.shopping.PurchaseHistory.purchaseDate"/></th>
                                <th><FormattedMessage id="project.shopping.PurchaseHistory.purchaseId"/></th>
                                <th><FormattedMessage id="project.shopping.PurchaseHistory.movie"/></th>
                                <th><FormattedMessage id="project.shopping.PurchaseHistory.numTickets"/></th>
                                <th><FormattedMessage id="project.shopping.PurchaseHistory.totalPrice"/></th>
                                <th><FormattedMessage id="project.shopping.PurchaseHistory.sessionDate"/></th>
                                <th><FormattedMessage id="project.shopping.PurchaseHistory.delivered"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            {purchaseSearch.items.map((purchase, index) =>
                                <tr key={purchase.id}>
                                    <td>
                                        <FormattedDate value={new Date(purchase.purchaseDate)}/>
                                        {' '}
                                        <FormattedTime value={new Date(purchase.purchaseDate)}/>
                                    </td>
                                    <td id={index === 0 ? 'purchase-history-first-id' : undefined}>{purchase.id}</td>
                                    <td id={index === 0 ? 'purchase-history-first-movie' : undefined}>{purchase.movieTitle}</td>
                                    <td>{purchase.numTickets}</td>
                                    <td>
                                        <FormattedNumber value={purchase.totalPrice} style="currency" currency="EUR"/>
                                    </td>
                                    <td>
                                        <FormattedDate value={new Date(purchase.sessionDate)}/>
                                        {' '}
                                        <FormattedTime value={new Date(purchase.sessionDate)}/>
                                    </td>
                                    <td>
                                        {purchase.delivered ? (
                                            <Badge bg="success">
                                                <FormattedMessage id="project.global.fields.yes"/>
                                            </Badge>
                                        ) : (
                                            <Badge bg="secondary">
                                                <FormattedMessage id="project.global.fields.no"/>
                                            </Badge>
                                        )}
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </Table>
                    <Pager
                        back={{
                            enabled: page > 0,
                            onClick: () => setPage(page - 1)
                        }}
                        next={{
                            enabled: purchaseSearch.existMoreItems,
                            onClick: () => setPage(page + 1)
                        }}/>
                </>
            )}
        </div>
    );

};

export default PurchaseHistory;
