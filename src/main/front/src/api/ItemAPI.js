import axios from "axios";

export const outfitItemDataAPI = (sensTemp) => {
	const response = axios.create({
			headers: {
				'temp': sensTemp
				}
		})
		.get(`/api/item/get`)
		.then((response) => {
			return response.data;
		}).catch(error => console.log(error));
	return response;
}

export const outfitPollDataAPI = (city, district) => {
	const response = axios.create({
			headers: {
				'city': city,
				'district': district
				}
		})
		.get(`/api/poll/get`)
		.then((response) => {
			return response.data;
		}).catch(error => console.log(error));
	return response;
}