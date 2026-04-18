import {appFetch} from './appFetch';

export const getBillboard = async date =>
    await appFetch('GET', `/catalog/movies?date=${encodeURIComponent(date)}`);
