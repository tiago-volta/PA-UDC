import {appFetch} from './appFetch';

export const getBillboard = async date =>
    await appFetch('GET', `/catalog/movies?date=${encodeURIComponent(date)}`);
export const findMovieById = async id =>
    await appFetch('GET', `/catalog/movies/${id}`);
export const findSessionById = async id =>
    await appFetch('GET', `/catalog/sessions/${id}`);
export const buyTickets = async (userId, sessionId, numTickets, bankCard) =>
    await appFetch('POST', `/catalog/sessions/${sessionId}/buyTickets`, {
        userId,
        numTickets,
        bankCard
    });