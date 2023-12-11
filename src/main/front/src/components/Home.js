import { useEffect, useState } from "react";
import { getLocaAuth } from "../api/GeolocationAPI";

function Home () {
	
	let [coords, setCoords] = useState({});
	let [weather, setWeather] = useState({});
	
	useEffect(() => {
	    const data = getLocaAuth();
	    
	    console.log("callback"+data);
	}, []);
	
	return (
		<h1>Home</h1>
	);
}

export default Home;