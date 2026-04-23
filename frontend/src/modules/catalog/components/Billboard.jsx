import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import Movies from './Movies';
import * as selectors from '../selectors';
import * as actions from '../actions';
import DateSelector from './DateSelector';
import backend from '../../../backend';
import catalog from '..';

const Billboard = () => {
    const movies = useSelector(selectors.getMovies);
    const billboardDate = useSelector(selectors.getBillboardDate);
    const dispatch = useDispatch();

    const handleBillboardDateChange = async date => {
        dispatch(catalog.actions.clearBillboard(date));
        const response = await backend.catalogService.getBillboard(date);
        if (response.ok) {
            dispatch(actions.getBillboardCompleted(response.payload));
        }
    };

    return (
        <div>
            <div className="d-flex align-items-center gap-2 mb-3">
                <FormattedMessage id='project.global.fields.date'/>
                <DateSelector
                    id="billboardDate"
                    className="w-auto"
                    value={billboardDate}
                    onChange={e => handleBillboardDateChange(e.target.value)}
                />
            </div>
            <Movies movies={movies}/>
        </div>
    );
};

export default Billboard;