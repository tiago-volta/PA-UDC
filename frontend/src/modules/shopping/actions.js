import * as actionTypes from './actionTypes';

export const buyCompleted = purchaseId => ({
    type: actionTypes.BUY_COMPLETED,
    purchaseId
});