import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    lastPurchaseId: null,
    lastDeliveredPurchaseId: null
};

const lastPurchaseId = (state = initialState.lastPurchaseId, action) => {
    switch (action.type) {
        case actionTypes.BUY_COMPLETED:
            return action.purchaseId;
        default:
            return state;
    }
};

const lastDeliveredPurchaseId = (state = initialState.lastDeliveredPurchaseId, action) => {
    switch (action.type) {
        case actionTypes.DELIVER_TICKETS_COMPLETED:
            return action.purchaseId;
        default:
            return state;
    }
};

const reducer = combineReducers({
    lastPurchaseId,
    lastDeliveredPurchaseId
});

export default reducer;