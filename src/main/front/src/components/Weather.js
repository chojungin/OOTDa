import { useEffect, useState } from "react";
import { reverseGeoAPI, forecastAPI } from "../api/GeoAPI";

import { WiCloudy, WiDaySunny, WiDust, WiDayHaze, WiFog, WiSandstorm, WiSmoke, WiTornado, WiRainWind, WiRain, WiDayRain, WiSnow, WiThunderstorm } from "react-icons/wi";
import { BsGeoAltFill } from "react-icons/bs";
import { FaRegThumbsUp } from "react-icons/fa";

import Container from "react-bootstrap/esm/Container";
import Card from 'react-bootstrap/Card';
import Stack from 'react-bootstrap/Stack';

import axios from "axios";

function Weather() {
	
	const [loading, setloading] = useState(false);
	const [text, setText] = useState({ city: '', district: '', temp: '', sensTemp: '', maxTemp: '', minTemp: '', des: '' });
	const [outfit, setOutfit] = useState([]);
	
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
					
					let lon = position.coords.longitude; //경도 x
					let lat = position.coords.latitude; //위도 y

					let reverseGeoData = await reverseGeoAPI(lon,lat);
					let forecastData = await forecastAPI(lon,lat);
					
					//console.log(forecastData);
					
					setText({
						city: reverseGeoData.region_1depth_name,
						district: reverseGeoData.region_2depth_name,
						temp: parseFloat(forecastData.main.temp).toFixed(1), 
						sensTemp: parseFloat(forecastData.main.feels_like).toFixed(0), 
						maxTemp: forecastData.main.temp_max, 
						minTemp: forecastData.main.temp_min, 
						des: forecastData.weather[0].main,
					});
					
					setloading(true);
					
					if (text.sensTemp !== ""){
						axios.create({
						    headers: {
						        'temp': text.sensTemp
						    },
						}).get(`/api/outfit/get`)
						.then((response) => {
							setOutfit(response.data);
						}).catch((error) => {
							console.log(error);
						});
					}
					
				},(error) => {
					
					switch(error.code) {
				        case error.PERMISSION_DENIED:
				        console.log("위치정보제공 동의에 거부하셨습니다.");
				        break;
				
				        case error.POSITION_UNAVAILABLE:
				        console.log("위치정보가 존재하지않습니다.");
				        break;
				
				        case error.TIMEOUT:
				        console.log("요청 시간이 초과되었습니다.");
				        break;
				
				        default:
				        console.log("오류가 발생하였습니다. 다시 시도해주시기 바랍니다.");
				    };
					
				},{ 
					timeout : 3600000, //3600000밀리초 = 60분
					enableHighAccuracy : true, //위치정확도
				}
			);
	}, [loading]);
	
	return (
		<div className="bg-dark text-light vh-100">
			<Container className="py-3">
				{ loading ? (
					<>
						<Stack direction="horizontal" gap={3}>
							<Card className="w-50 position-relative" bg='primary' text='light'>
								<Card.Body >
									<Card.Title className="fs-3"><BsGeoAltFill />{text.district}</Card.Title>
									<Card.Text className="fs-4">{text.temp}°</Card.Text>
								</Card.Body>
								<div className="position-absolute bottom-0 end-0 pe-2 pb-2">{icons[text.des]}</div>
							</Card>
							<Card className="w-auto" bg='warning' text='dark'>
								<Card.Body>
									<Card.Title className="fs-3"><FaRegThumbsUp />오늘 뭐입지?</Card.Title>
									<Card.Text>{outfit}</Card.Text>
								</Card.Body>
							</Card>
						</Stack>
					</>
				) : (
					<>
						<Card className="w-50 position-relative" bg='secondary' text='light'>
							<Card.Body >
								<Card.Title className="fs-3"><BsGeoAltFill />위치정보없음</Card.Title>
							</Card.Body>
						</Card>
					</>
				)}
			</Container>
		</div>
	);
}

export default Weather;