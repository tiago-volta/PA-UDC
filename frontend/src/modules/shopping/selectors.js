const getModuleState = state => state.shopping;

export const getLastPurchaseId = state => 
    getModuleState(state).lastPurchaseId;