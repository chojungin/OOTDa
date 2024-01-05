import NavBar from "./NavBar";
import Weather from "./Weather";
import Poll from "./Poll";
import Container from "react-bootstrap/esm/Container";
import Stack from 'react-bootstrap/Stack';

function Home () {
	
	return (
		<div className="bg-dark text-light">
			<Container className="vh-100" style={{paddingTop: '1rem', paddingBottom: '6rem',  overflowY: 'scroll'}}>
				<Stack gap={3}>
					<Weather />
				</Stack>
			</Container>
			<NavBar />
		</div>
			
	);
}

export default Home;