import * as actionTypes from './actionTypes';

export const buyCompleted = purchaseId => ({
    type: actionTypes.BUY_COMPLETED,
    purchaseId
});

export const deliverTicketsCompleted = purchaseId => ({
    type: actionTypes.DELIVER_TICKETS_COMPLETED,
    purchaseId
});