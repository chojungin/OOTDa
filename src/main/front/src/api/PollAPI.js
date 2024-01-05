import axios from "axios";

export const getItemPollData = async (sensTemp) => {
	debugger;
	await axios.create({headers: {'temp': sensTemp},}).get(`/api/item/get`)
		.then((response) => {
			return response.data;
		}).catch((error) => {
			console.log(error);
		});
}