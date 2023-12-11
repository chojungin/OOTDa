
const API_KEY = '1a2d3e0910911685db6f4a8269c26907'; //무료api
let lat;
let lon;
	
export const getLocaAuth = async () => {
	navigator.geolocation.getCurrentPosition(
		(position) => { //위치제공동의
			lat = position.coords.latitude;
		    lon = position.coords.longitude;
		    return fetchAPI(lat, lon);
		}, () => console.log("위치제공동의거부") //위치제공동의거부
	);
};

const fetchAPI = async (lat, lon) => {
    let response = await fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric`);
    let data = await response.json();
	return data;
};