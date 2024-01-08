import { useEffect, useState } from "react";
//import { useQuery } from 'react-query';
import axios from "axios";
import { reverseGeoAPI, forecastAPI } from "../api/GeoAPI";
import { outfitItemDataAPI, outfitPollDataAPI } from "../api/OutfitAPI";

import { WiCloudy, WiDaySunny, WiDust, WiDayHaze, WiFog, WiSandstorm, WiSmoke, WiTornado, WiRainWind, WiRain, WiDayRain, WiSnow, WiThunderstorm } from "react-icons/wi";
import { BsGeoAltFill, BsFillChatDotsFill, BsCheckCircleFill, BsCheckLg } from "react-icons/bs";

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Badge from 'react-bootstrap/Badge';
import ListGroup from 'react-bootstrap/ListGroup';

function Weather() {
	
	const year = new Date().getFullYear();
	const month = (new Date().getMonth() + 1).toString().padStart(2, '0');
	const day = new Date().getDate().toString().padStart(2, '0');
	const now = `${year}-${month}-${day}`;
	
	//기상에 따라 표출될 기상 아이콘
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
	
	//날씨
	const [text, setText] = useState({city: '', district: '', temp: '', sensTemp: '', maxTemp: '', minTemp: '', des: ''});
	const [items, setItem] = useState([]); //기온에 따른 착장 아이템
	
	//설문
	const [pollItems, setPollItems] = useState([]); //기온별 착장 아이템 설문
	const [selectedItems, setSelectedItems] = useState([]); //설문에서 선택한 착장 아이템
	const [pollResult, setPollResult] = useState([]); //기온별 착장 아이템 설문 결과
	const [maxCountItemId, setMaxCountItemId] = useState([]);
	
	const [loading, setLoading] = useState(false); //로딩
	const [pollYn, setPollYn] = useState(false); //설문여부
	
	const FetchData = async () => {
		
		try {
			const position = await FetchCurrentPositionData();
			PositionCallback(position);
			
		} catch (error) {
			PositionErrorCallback(error);
		}
	}
	
	const pollYnCheck = async () => {

		if (
			localStorage.getItem("pollDate") === now &&
			localStorage.getItem("pollCity") === text.city &&
			localStorage.getItem("pollDistrict") === text.district &&
			localStorage.getItem("selectedItems") !== ''
		) {
			setPollYn(true);
			setSelectedItems(localStorage.getItem("selectedItems"));
			
			const result = await outfitPollDataAPI(text.city, text.district);
			console.log(result);			
			setPollResult(result);
			
		} else if (localStorage.getItem("pollDate") !== now){
			
			localStorage.removeItem("pollDate");
			localStorage.removeItem("pollCity");
			localStorage.removeItem("pollDistrict");
			localStorage.removeItem("selectedItems");
		}
	}
	
	const FetchCurrentPositionData = () => {
		
		return new Promise((resolve, reject) => {
			navigator.geolocation.getCurrentPosition(
				resolve, 
				reject,
				{
					timeout : 3600000, //3600000밀리초 = 60분
					enableHighAccuracy : true, //위치정확도
				}
			);
		});
	}
	
	const PositionCallback = async (position) => {
		
		const lon = position.coords.longitude; //경도 x
		const lat = position.coords.latitude; //위도 y
		
		const reverseGeoData = await reverseGeoAPI(lon,lat);
		const forecastData = await forecastAPI(lon,lat);
		const outfitItemData = await outfitItemDataAPI(parseFloat(forecastData.main.feels_like).toFixed(0));
		
		console.log(reverseGeoData);
		console.log(forecastData);
		console.log(outfitItemData);
		
		setText({
			city: reverseGeoData.region_1depth_name,
			district: reverseGeoData.region_2depth_name,
			temp: parseFloat(forecastData.main.temp).toFixed(1), 
			sensTemp: parseFloat(forecastData.main.feels_like).toFixed(0), 
			maxTemp: forecastData.main.temp_max, 
			minTemp: forecastData.main.temp_min, 
			des: forecastData.weather[0].main,
		});
		setItem(outfitItemData);
		setPollItems(outfitItemData);
		setLoading(true);
	}
	
	const PositionErrorCallback = async (error) => {
		
		switch(error.code) {
	        case error.PERMISSION_DENIED:
	        console.log(error.code+"위치정보제공 동의에 거부하셨습니다.");
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
	}
	
	//설문 선택
	const onSelectPollItem = async (id) => {
		
		const updatedSelection = [...selectedItems];
		if (updatedSelection.includes(id)) {
		  updatedSelection.splice(updatedSelection.indexOf(id), 1);
		} else {
		  updatedSelection.push(id);
		}
		setSelectedItems(updatedSelection);
	};
	
	//설문 제출
	const onClickSubmitButton = async () => {
		
		await axios.post(`/api/poll/post`, {
			city : text.city,
			district : text.district,
			sensTemp : text.sensTemp,
			itemIdList : selectedItems
		}).then((response) => {
			
			console.log(response.data); //0: {itemId: 9, itemName: '울코트', itemCount: 1, pollDate: '2024-01-05'}
			
			setPollResult(response.data);
			setPollYn(true);
			
			localStorage.setItem("pollDate", response.data[0].pollDate);
			localStorage.setItem("pollCity", text.city);
			localStorage.setItem("pollDistrict", text.district);
			localStorage.setItem("selectedItems", selectedItems);
			
		}).catch((error) => {
			console.log(error);
		});
	}
	
	useEffect(() => {
		FetchData();
	}, []);
	
	useEffect(() => {
		pollYnCheck();
	}, [loading]);
	
	useEffect(() => {
		
		if (pollResult.length > 0) {
			const maxCount = Math.max(...pollResult.map(result => result.itemCount));
			const maxItems = pollResult.filter(result => result.itemCount === maxCount);
			const maxIds = maxItems.map(item => item.itemId);
			setMaxCountItemId(maxIds);
		}
	}, [pollResult]);
	
	
	if (!loading){
		return (
			<Card className="w-50 position-relative" bg='secondary' text='light'>
				<Card.Body >
					<Card.Title className="fs-3"><BsGeoAltFill />위치정보없음</Card.Title>
				</Card.Body>
			</Card>
		);
	}
	
	return (
		<>
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
				{items && items.map((badgeItem) => (
		            <Badge bg="primary" text="light" className="border border-light m-1 fs-6" key={badgeItem.id}>
		                {badgeItem.itemName}
		            </Badge>
		        ))}
			</Card.Body>
		</Card>
		{!pollYn ? (
			<Card bg='primary' text='light'>
				<Card.Body>
					<Card.Title className="fs-3 d-flex align-items-center"><BsCheckCircleFill size='25' className="me-2 top"/>오늘 뭘 입으셨나요?</Card.Title>
				</Card.Body>
				<ListGroup>
					{pollItems && pollItems.map((pollItem) => (
						<ListGroup.Item
							action
							variant={selectedItems.includes(pollItem.id) ? 'primary' : 'light'}
							className={selectedItems.includes(pollItem.id) ? 'text-primary' : ''}
							key={pollItem.id}
							onClick={() => onSelectPollItem(pollItem.id)}
						>
							{selectedItems.includes(pollItem.id) && <BsCheckLg className="me-2"/>} {pollItem.itemName}
						</ListGroup.Item>
					))}
				</ListGroup>
				<Button variant="primary" size="lg" onClick={()=> onClickSubmitButton()}>제출</Button>
			</Card>
		):(
			<Card bg='primary' text='light'>
				<Card.Body>
					<Card.Title className="fs-3 d-flex align-items-center">
						<BsCheckCircleFill size='25' className="me-2 top"/>
						오늘 사람들은 뭐 입었지? 
					</Card.Title>
				</Card.Body>
				<ListGroup>
					{pollItems && pollItems.map((pollItem) => (
						<ListGroup.Item
							style={{ backgroundColor: maxCountItemId.includes(pollItem.id) ? '#ced4da' : '' }}
							className="position-relative"
							key={pollItem.id}
						>
							{selectedItems.includes(pollItem.id) && <BsCheckLg className="me-2"/>} {pollItem.itemName}
							{pollResult && pollResult.map((result) => (
			                    result.itemId === pollItem.id ? (
									<small className="position-absolute end-0 pe-3" key={result.itemId}>({result.itemCount}표)</small>
								) : ('') )
							)}
						</ListGroup.Item>
					))}
				</ListGroup>
				<Card.Footer className="text-end small">{now} {text.city} {text.district} 기준</Card.Footer>
			</Card>
		)}
		</>
	);
}

export default Weather;