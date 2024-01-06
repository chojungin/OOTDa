import axios from "axios";

export const outfitItemDataAPI = (sensTemp) => {
	const response = axios.create({
			headers: {
				'Content-Type': 'application/json',
				'temp': sensTemp
				}
		})
		.get(`/api/item/get`)
		.then((response) => {
			return response.data;
		}).catch(error => console.log(error));
	return response;
}