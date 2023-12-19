import { Link, Route, Routes,} from 'react-router-dom';

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

import { BsHouse, BsBookmark, BsBell, BsThreeDots } from "react-icons/bs";

import Home from './Home';
import Login from './Login';
import Join from './Join';
import Setting from './Setting';
import Member from './Member';

function NavBar() {
	return (
		<Navbar bg="dark" data-bs-theme="dark" fixed='bottom' className="px-5">
			<Nav className="w-100 justify-content-between px-1 py-2">
				<Link to="/"><BsHouse className="fs-1"/></Link>
				<Link to="/"><BsBookmark className="fs-1"/></Link>
				<Link to="/"><BsBell className="fs-1"/></Link>
				<Link to="/setting"><BsThreeDots className="fs-1"/></Link>
			</Nav>
		</Navbar>
	);
}

function App() {
  return (
	  <>
      <NavBar/>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route path="/setting" element={<Setting />} />
        <Route path="/member" element={<Member />} />
      </Routes>
      </>
  );
}

export default App;