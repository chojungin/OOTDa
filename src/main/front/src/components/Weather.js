import { useEffect, useState } from "react";
import axios from "axios";
import { reverseGeoAPI, forecastAPI } from "../api/GeoAPI";

import { WiCloudy, WiDaySunny, WiDust, WiDayHaze, WiFog, WiSandstorm, WiSmoke, WiTornado, WiRainWind, WiRain, WiDayRain, WiSnow, WiThunderstorm } from "react-icons/wi";
import { BsGeoAltFill, BsFillChatDotsFill, BsCheckCircleFill, BsCheckLg } from "react-icons/bs";

import Card from 'react-bootstrap/Card';
import Stack from 'react-bootstrap/Stack';
import Button from 'react-bootstrap/Button';
import Badge from 'react-bootstrap/Badge';
import ListGroup from 'react-bootstrap/ListGroup';

function Weather() {
	
	const [loading, setloading] = useState(false);
	const [pollYn, setPollYn] = useState(false);
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
	
	const [items, setItem] = useState([]); //오늘 뭐 입지?
	const [pollItems, setPollItems] = useState([]); //오늘 뭘 입으셨나요?
	const [pollResult, setPollResult] = useState([]); //@@사람들은 오늘 뭘 입었지?
	
	const weatherAndPollData = async () => {
		
		//날씨
		navigator.geolocation.getCurrentPosition(
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
				
				//설문
				if (text.sensTemp !== ""){
					axios.create({headers: {'temp': text.sensTemp}}).get(`/api/item/get`) 
					.then((response) => {
						setItem(response.data);
						setPollItems(response.data);
						setloading(true);
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
	}
	
	//설문선택
	const [selectItems, setSelectItems] = useState([]);
	const selectItemHandler = (id) => {
		const updatedSelection = [...selectItems];
		if (updatedSelection.includes(id)) {
		  updatedSelection.splice(updatedSelection.indexOf(id), 1);
		} else {
		  updatedSelection.push(id);
		}
		setSelectItems(updatedSelection);
	};
	
	//설문제출
	const submitPoll = async () => {
		await axios.post(`/api/poll/post`, {
			city : text.city,
			district : text.district,
			sensTemp : text.sensTemp,
			itemIdList : selectItems
		}).then((response) => {
			
			console.log(response.data); //0: {itemId: 9, itemName: '울코트', itemCount: 1, pollDate: '2024-01-05'}
			//설문제출 완료
			setPollResult(response.data);
			setPollYn(true);
			//localStorage.setItem("pollYn", "Y");
			//localStorage.setItem("pollDate", pollResult[0].pollDate);
			
		}).catch((error) => {
			console.log(error);
		});
	}
	
	useEffect(() => {
		weatherAndPollData();
	},[]);
	
	return (
			<>
			{loading === true ? (
				<>
					{/*현재 위치 또는 설정한 위치 날씨 및 온도 정보 표출*/}
					<Card className="w-auto h-100 position-relative" bg='primary' text='light'>
						<Card.Body >
							<Card.Title className="fs-3 d-flex align-items-center"><BsGeoAltFill size='25' className="me-2 top"/>{text.district}</Card.Title>
							<Card.Text className="fs-4">{text.temp}°</Card.Text>
						</Card.Body>
						<div className="position-absolute bottom-0 end-0 pe-2 pb-2">{icons[text.des]}</div>
					</Card>
					<Card className="w-auto h-100" bg='primary' text='light'>
						<Card.Body>
							<Card.Title className="fs-3 d-flex align-items-center"><BsFillChatDotsFill size='25' className="me-2 top"/>오늘 뭐 입지?</Card.Title>
							{items.map((badgeItem) => (
					            <Badge bg="primary" text="light" className="border border-light m-1 fs-6" key={badgeItem.id}>
					                {badgeItem.itemName}
					            </Badge>
					        ))}
						</Card.Body>
					</Card>
					{pollYn === true ? (
						<Card bg='primary' text='light'>
							<Card.Body>
								<Card.Title className="fs-3 d-flex align-items-center">
									<BsCheckCircleFill size='25' className="me-2 top"/>
									오늘 {text.district} 사람들은 뭐 입었지?
								</Card.Title>
							</Card.Body>
							<ListGroup bg='secondary'>
								{pollItems.map((pollItem) => (
									<ListGroup.Item
										className="position-relative"
										variant="secondary"
										key={pollItem.id}>
										{selectItems.includes(pollItem.id) && <BsCheckLg className="me-2"/>} {pollItem.itemName}
										{pollResult.map((result) => (
						                    result.itemId === pollItem.id ?(
												<span className="position-absolute end-0 pe-3" style={{fontSize: 'small'}} key={result.itemId}>({result.itemCount}표)</span>
											) : '' )
										)}
									</ListGroup.Item>
								))}
							</ListGroup>
						</Card>
					):(
						<Card bg='primary' text='light'>
							<Card.Body>
								<Card.Title className="fs-3 d-flex align-items-center"><BsCheckCircleFill size='25' className="me-2 top"/>오늘 뭘 입으셨나요?</Card.Title>
							</Card.Body>
							<ListGroup>
								{pollItems.map((pollItem) => (
									<ListGroup.Item
										action
										variant={selectItems.includes(pollItem.id) ? 'primary' : 'light'}
										className={selectItems.includes(pollItem.id) ? 'text-primary' : ''}
										key={pollItem.id}
										onClick={() => selectItemHandler(pollItem.id)}>
										{selectItems.includes(pollItem.id) && <BsCheckLg className="me-2"/>} {pollItem.itemName}
									</ListGroup.Item>
								))}
							</ListGroup>
							<Button variant="primary" size="lg" onClick={()=> submitPoll()}>제출</Button>
						</Card>
					)}
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
			</>
	);
}

export default Weather;