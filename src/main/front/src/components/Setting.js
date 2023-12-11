import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';

function Setting (){
	
	const ACCESS_TOKEN = localStorage.getItem('accessToken');
	const userName = localStorage.getItem('userName');
	const roleType = localStorage.getItem('roleType');
	
	//로그아웃
	const onClickLogout = async (e) => {
		
		e.preventDefault();
		
		if (window.confirm("로그아웃 하시겠습니까?")){
			localStorage.clear();
			window.location.href = "/";
		}
	}
	
	const navigate = useNavigate();
	useEffect((link) => {
	    navigate(link);
	}, [navigate]);
	
	return (
		<div className='container'>
			<h1>Setting</h1>
			{ACCESS_TOKEN ? (
				<div className="d-grid gap-2">
					<h4>{userName} : {roleType === 'ROLE_USER' ? '일반 사용자' : '관리자'}</h4>
					<Button variant="outline-primary" size="lg">메뉴1</Button>
					<Button variant="outline-primary" size="lg">메뉴2</Button>
					<Button variant="outline-secondary" size="lg" onClick={onClickLogout}>Logout</Button>
				</div>
			):(
				<div className="d-grid gap-2">
					<Button variant="primary" size="lg" onClick={()=>navigate('/login')}>Login</Button>
					<Button variant="link" size="lg" onClick={()=>navigate('/join')}>Join</Button>
				</div>
			)}
    	</div>
	)
}

export default Setting;
