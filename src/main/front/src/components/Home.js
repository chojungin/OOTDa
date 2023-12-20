import NavBar from "./NavBar";
import Weather from "./Weather";

function Home () {
	
	return (
		<div className="bg-dark text-light vh-100">
			<Weather />
			<NavBar />
		</div>
	);
}

export default Home;