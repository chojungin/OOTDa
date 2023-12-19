import { useEffect, useState } from "react";
import { reverseGeoAPI, forecastAPI } from "../api/GeoAPI";

import { WiCloudy, WiDaySunny, WiDust, WiDayHaze, WiFog, WiSandstorm, WiSmoke, WiTornado, WiRainWind, WiRain, WiDayRain, WiSnow, WiThunderstorm } from "react-icons/wi";
import { BsGeoAltFill } from "react-icons/bs";

import Container from "react-bootstrap/esm/Container";
import Card from 'react-bootstrap/Card';
import NavBar from "./NavBar";

function Home () {
	
	const [loading, setloading] = useState(false);
	const [text, setText] = useState({ city: '', district: '', temp: '', sensTemp: '', maxTemp: '', minTemp: '', des: '' });
	const icons = {
	  "Thunderstorm" : <WiThunderstorm size="80" opacity="0.7"/>,
	  "Drizzle" : <WiDayRain size="80" opacity="0.7"/>,
	  "Rain" : <WiRain size="80" opacity="0.7"/>,
	  "Snow" : <WiSnow size="80" opacity="0.7"/>,
	  "Atmosphere" : <WiDust size="80" opacity="0.7"/>,
	  "Mist" : <WiFog size="80" opacity="0.7" />,
	  "Smoke" : <WiSmoke size="80" opacity="0.7"/>,
	  "Haze" : <WiDayHaze size="80" opacity="0.7"/>,
	  "Dust" : <WiDust size="80" opacity="0.7"/>,
	  "Fog" : <WiFog size="80" opacity="0.7"/>,
	  "Sand" : <WiSandstorm size="80" opacity="0.7"/>,
	  "Ash" : <WiDust size="80" opacity="0.7"/>,
	  "Squall" : <WiRainWind size="80" opacity="0.7"/>,
	  "Tornado" : <WiTornado size="80" opacity="0.7"/>,
	  "Clear" : <WiDaySunny size="80" opacity="0.7"/>,
	  "Clouds" : <WiCloudy size="80" opacity="0.7"/>
	};
	
	useEffect(() => {
		navigator.geolocation
			.getCurrentPosition(
				async (position) => {
					console.log("위치정보 제공동의");
					
					let lon = position.coords.longitude; //경도 x
					let lat = position.coords.latitude; //위도 y

					let reverseGeoData = await reverseGeoAPI(lon,lat);
					let forecastData = await forecastAPI(lon,lat);
					
					setText({
						city: reverseGeoData.region_1depth_name,
						district: reverseGeoData.region_2depth_name,
						temp: forecastData.main.temp, 
						sensTemp: forecastData.main.feels_like, 
						maxTemp: forecastData.main.temp_max, 
						minTemp: forecastData.main.temp_min, 
						des: forecastData.weather[0].main,
					});
					
					setloading(true);
				    
				},
				() => console.log("위치정보 제공동의거부"),
				{ enableHighAccuracy : true, maximumAge : 30000, }
			);
	}, [loading]);
	
	return (
		<div className="bg-dark text-light vh-100">
			<Container className="py-3">
				{ loading ? (
					<>
						<Card className="w-50 position-relative" bg='primary' text='light'>
							<Card.Body >
								<Card.Title className="fs-3"><BsGeoAltFill /> {text.district}</Card.Title>
								<Card.Text className="fs-4">{parseFloat(text.temp).toFixed(1)}°</Card.Text>
							</Card.Body>
							<div className="position-absolute bottom-0 end-0 pe-3 pb-2">{icons[text.des]}</div>
						</Card>
					</>
				) : (
					<>
						<Card className="w-50 position-relative" bg='secondary' text='light'>
							<Card.Body >
								<Card.Title className="fs-3"><BsGeoAltFill /> 위치정보없음</Card.Title>
							</Card.Body>
						</Card>
					</>
				)}
			</Container>
			<NavBar />
		</div>
	);
}

export default Home;