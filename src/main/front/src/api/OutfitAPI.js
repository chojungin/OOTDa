import axios from "axios";

export const outfitItemDataAPI = (sensTemp) => {
	const response = axios.get(`/api/item/get`, {
						params : {
							'temp': sensTemp
						}})
					.then((response) => { return response.data; })
					.catch(error => console.log(error));
	return response;
}

export const outfitPollDataAPI = (city, district) => {
	const response = axios.get(`/api/poll/get`, {
						params : {
							'city': city,
							'district': district
						}})
					.then((response) => { return response.data; })
					.catch(error => console.log(error));
	return response;
}