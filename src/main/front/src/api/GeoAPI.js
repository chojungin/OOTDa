import axios from "axios";

const WEATHER_API_KEY = '1a2d3e0910911685db6f4a8269c26907';
const REVERSE_GEO_API_KEY = '30c9bd93c84b326ae5887110c363cc43';

export const reverseGeoAPI = (longitude, latitude) => {
	const response = axios
		.get(`https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?input_coord=WGS84&x=${longitude}&y=${latitude}`
				,{ headers : {
					'Content-Type' : 'application/json;charset=UTF-8',
			        'Authorization' : `KakaoAK ${REVERSE_GEO_API_KEY}`,
			    },
			})
		.then(response => {
	    	return response.data.documents[0];
		}).catch(error => console.log(error.message));
	return response;
};

export const forecastAPI = (longitude, latitude) => {
	const response =  axios
		.get(`https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${WEATHER_API_KEY}&units=metric`)
	    .then(response => {
	        return response.data;
		}).catch(error => console.log(error.message));
	return response;
};