import axios from "axios";

let TOKEN_TYPE = localStorage.getItem("tokenType");
let ACCESS_TOKEN = localStorage.getItem("accessToken"); 
const REFRESH_TOKEN = localStorage.getItem("refreshToken");

export const tokenAPI = axios.create({
    headers: {
        'Content-Type': 'application/json',
        'Authorization': TOKEN_TYPE + " " + ACCESS_TOKEN,
        'REFRESH_TOKEN': REFRESH_TOKEN,
    },
});

export const getMember = async () => {
    const response = await tokenAPI.get('/api/user/get');
    return response.data;
}

export const putMember = async (data) => {
    const response = await tokenAPI.put('/api/user/put', data);
    return response.data;
}

export const deleteMember = async () => {
    await tokenAPI.delete('/api/user/delete');
}

//AccessToken을 refresh
const refreshAccessToken = async () => {
	await tokenAPI.get('/api/auth/refresh')
	    .then((response) => { 
			//refresh 성공
			ACCESS_TOKEN = response.data;
			localStorage.setItem('accessToken', ACCESS_TOKEN);
			tokenAPI.defaults.headers.common['Authorization'] = `${TOKEN_TYPE} ${ACCESS_TOKEN}`;
		});
}

export const clearLoginInfo = async () => {
	localStorage.removeItem("tokenType");
	localStorage.removeItem("accessToken");
	localStorage.removeItem("refreshToken");
	localStorage.removeItem("userName");
	localStorage.removeItem("roleType");
}

//tokenAPI 인터셉터
tokenAPI.interceptors.response.use(
	(response) => {
    	return response;
	},
	async (error) => {
		
		if (error.config && error.response) {
			
			if (error.response.status === 403) { //403 Forbidden : AccessToken이 유효하지 않은 상태
				
				console.log("tokenAPI 403 "+error);
				await refreshAccessToken();
				return tokenAPI(error.config);
		        
		    } else if (error.response.status === 401) { //401 Unauthorized : RefreshToken이 유효하지 않은 상태
				//localStorage.clear();
				await clearLoginInfo();
				alert("로그인이 만료되어 로그아웃 되었습니다.");
				window.location.href = "/";
			}
		}
	    return Promise.reject(error);
	}
);


