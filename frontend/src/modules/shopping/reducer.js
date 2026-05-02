import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    lastPurchaseId: null
};

const lastPurchaseId = (state = initialState.lastPurchaseId, action) => {
    switch (action.type) {
        case actionTypes.BUY_COMPLETED:
            return action.purchaseId;
        default:
            return state;
    }
};

const reducer = combineReducers({
    lastPurchaseId
});

export default reducer;