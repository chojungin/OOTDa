import { useEffect, useState } from "react";
import { reverseGeoAPI, forecastAPI } from "../api/GeoAPI";
import { WiDegrees, WiCloudy, WiDaySunny, WiDust, WiRain, WiDayRain, WiSnow, WiThunderstorm } from "react-icons/wi";

function Home () {
	
	const [loading, setloading] = useState(true);
	const [text, setText] = useState({ city: '', district: '', temp: '', sensTemp: '', maxTemp: '', minTemp: '', des: '' });
	const icons = {
	  "Thunderstorm" : <WiThunderstorm />,
	  "Drizzle" : <WiDayRain />,
	  "Rain" : <WiRain />,
	  "Snow" : <WiSnow />,
	  "Atmosphere" : <WiDust />,
	  "Clear" : <WiDaySunny />,
	  "Clouds" : <WiCloudy />,
	  "degrees" : <WiDegrees />
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
						des: forecastData.weather[0].main
					});
					
					setloading(true);
				    
				},
				() => console.log("위치정보 제공동의거부"),
				{ enableHighAccuracy : true, maximumAge : 30000, }
			);
	}, []);
	
	return (
		<>
			{loading ? (
				<>
					<h1>내 위치</h1>
					<h6>{text.city} {text.district}</h6>
					
					<h1>{icons[text.des]}</h1>
					<h6>{text.des}</h6>
					
					<h2>{text.temp}{icons.degrees}</h2>
					<h6>{text.maxTemp}/{text.minTemp}</h6>
					
				</>
			) : <h1>위치정보 제공동의를 거부하셨습니다.</h1>}
		</>
	);
}

export default Home;