import {appFetch} from './appFetch';

export const findPurchases = async page =>
    await appFetch('GET', `/purchases?page=${page}`);

export const deliverTickets = async (purchaseId, bankCard) =>
    await appFetch('POST', `/purchases/${purchaseId}/deliver`, {
        bankCard
    });
