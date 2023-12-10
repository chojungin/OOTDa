import axios from "axios";

const TOKEN_TYPE = localStorage.getItem("tokenType");
let ACCESS_TOKEN = localStorage.getItem("accessToken"); 
let REFRESH_TOKEN = localStorage.getItem("refreshToken");

export const tokenAPI = axios.create({
    //baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `${TOKEN_TYPE} ${ACCESS_TOKEN}`,
        'REFRESH_TOKEN': REFRESH_TOKEN,
    },
});

export const getMember = async () => {
	if (ACCESS_TOKEN) {
	    const response = await tokenAPI.get('/api/user/get');
	    return response.data;
    }
}
export const putMember = async (data) => {
    const response = await tokenAPI.put('/api/user/put', data);
    return response.data;
}
export const deleteMember = async () => {
    await tokenAPI.delete('/api/user/delete');
}

const refreshAccessToken = async () => {
    const response = await tokenAPI.get('/api/auth/refresh');
    ACCESS_TOKEN = response.data;
    localStorage.setItem('accessToken', ACCESS_TOKEN);
    tokenAPI.defaults.headers.common['Authorization'] = `${TOKEN_TYPE} ${ACCESS_TOKEN}`;
}

tokenAPI.interceptors.response.use((response) => {
    return response;
}, async (error) => {
    if (error.config && error.response && error.response.status === 401) {
        await refreshAccessToken();
        return tokenAPI(error.config);
    }
    return Promise.reject(error);
});
