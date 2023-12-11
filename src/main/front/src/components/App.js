import { Route, Routes,} from 'react-router-dom';
import { useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { BsEmojiSunglassesFill } from "react-icons/bs";

import Home from './Home';
import Login from './Login';
import Join from './Join';
import Setting from './Setting';


function NavBar() {
	
	return (
		<Navbar bg="dark" data-bs-theme="dark">
        <Container>
          <Navbar.Brand href="/"><BsEmojiSunglassesFill/></Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link href="/">Home</Nav.Link>
            <Nav.Link href="setting">Setting</Nav.Link>
          </Nav>
        </Container>
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
      </Routes>
      </>
  );
}

export default App;