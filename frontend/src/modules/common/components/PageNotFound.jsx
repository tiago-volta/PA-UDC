import Alert from 'react-bootstrap/Alert';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router';

const PageNotFound = () => (
    <Alert variant="danger" className="mt-3">
        <Alert.Heading>
            <FormattedMessage id='project.common.PageNotFound.title'/>
        </Alert.Heading>
        <p className="mb-0">
            <FormattedMessage id='project.common.PageNotFound.message'/>{' '}
            <Link to="/">
                <FormattedMessage id='project.app.Header.home'/>
            </Link>
        </p>
    </Alert>
);

export default PageNotFound;
