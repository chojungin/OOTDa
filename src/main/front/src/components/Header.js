import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

import { BsArrowLeft } from "react-icons/bs";

function Header (){
	
	const [pageName, setPageName] = useState('');
	
	useEffect(() => {
		
		const path = (window.location.pathname).split('/');
	    setPageName(path);
	    
	}, []);
	
	const navigate = useNavigate();
	
	return (
      <Navbar bg="light" data-bs-theme="light" fixed='top' className="px-3 position-relative">
      	<Nav className="w-100 px-1 py-2 position-relative">
	      	<BsArrowLeft
	      		className="position-absolute start-0 top-50 translate-middle-y"
	      		size="30"
	      		onClick={() => navigate(-1)}
	  		/>
	  		<h1 className='text-uppercase w-100'>{pageName}</h1>
  		</Nav>
      </Navbar>
  );
}

export default Header;