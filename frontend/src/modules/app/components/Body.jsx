import {useSelector} from 'react-redux';
import {Route, Routes} from 'react-router';
import Container from 'react-bootstrap/Container';
import {MovieDetails, SessionDetails} from '../../catalog';
import {PageNotFound} from '../../common';
import {DeliverTickets, DeliverTicketsCompleted, PurchaseCompleted, PurchaseHistory} from '../../shopping';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout} from '../../users';
import users from '../../users';

const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const userRole = useSelector(users.selectors.getUserRole);
    
   return (

       <Container className="my-4 justify-content-center flex-grow-1">
            <AppGlobalComponents/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/catalog/movie-details/:id" element={<MovieDetails/>}/>
                <Route path="/catalog/session-details/:id" element={<SessionDetails/>}/>
                {loggedIn && <Route path="/users/update-profile" element={<UpdateProfile/>}/>}
                {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
                {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
                {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
                {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
                {loggedIn && <Route path="/shopping/purchase-completed" element={<PurchaseCompleted/>}/>}
                {userRole === 'SPECTATOR' && <Route path="/shopping/purchase-history" element={<PurchaseHistory/>}/>}
                {userRole === 'TICKET_SELLER' && <Route path="/shopping/deliver-tickets" element={<DeliverTickets/>}/>}
                {userRole === 'TICKET_SELLER' && <Route path="/shopping/deliver-tickets-completed" element={<DeliverTicketsCompleted/>}/>}
                <Route path="/*" element={<PageNotFound/>}/>
            </Routes>
       </Container>

    );

};

export default Body;
