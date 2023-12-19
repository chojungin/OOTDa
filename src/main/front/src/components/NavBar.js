import { Link } from 'react-router-dom';

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

import { BsHouse, BsBookmark, BsBell, BsThreeDots } from "react-icons/bs";

function NavBar() {
	return (
		<Navbar bg="dark" data-bs-theme="dark" fixed='bottom' className="px-5">
			<Nav className="w-100 justify-content-between px-1 py-2">
				<Link to="/" className='nav-link'><BsHouse className="fs-1"/></Link>
				<Link to="/" className='nav-link'><BsBookmark className="fs-1"/></Link>
				<Link to="/" className='nav-link'><BsBell className="fs-1"/></Link>
				<Link to="/setting" className='nav-link'><BsThreeDots className="fs-1"/></Link>
			</Nav>
		</Navbar>
	);
}

export default NavBar;