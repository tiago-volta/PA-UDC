import {appFetch} from './appFetch';

export const findPurchases = async page =>
    await appFetch('GET', `/purchases?page=${page}`);
