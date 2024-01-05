import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Stack from 'react-bootstrap/Stack';
import Image from 'react-bootstrap/Image';
import Container from 'react-bootstrap/esm/Container';

import { AiTwotoneCrown } from "react-icons/ai";
import profile from '../images/default.jpg';
import NavBar from './NavBar';
import { clearLoginInfo } from '../api/TokenAPI';

function Setting (){
	
	const ACCESS_TOKEN = localStorage.getItem('accessToken');
	const userName = localStorage.getItem('userName');
	const roleType = localStorage.getItem('roleType');
	
	//로그아웃
	const onClickLogout = async (e) => {
		
		e.preventDefault();
		
		if (window.confirm("로그아웃 하시겠습니까?")){
			//localStorage.clear();
			clearLoginInfo();
			window.location.href = "/";
		}
	}
	
	const navigate = useNavigate();
	
	useEffect((link) => {
		
	    navigate(link);
	    
	}, [navigate]);
	
	return (
		<div className="bg-dark text-light vh-100">
			<Container className="py-3">
				{ACCESS_TOKEN ? (
					<>
					<Stack direction="vertical" gap={2}>
						<Stack direction="horizontal" gap={4}>
				        	<Image src={profile} roundedCircle style={{ width: '100px', height : '100px'}}/>
				        	<div>
				        		<div className="fs-1 fw-bolder text-primary">{userName}</div>
				        		<div className="fs-5 fw-normal text-secondary"><AiTwotoneCrown />{roleType === 'ROLE_USER' ? '일반 사용자' : '관리자'}</div>
				        	</div>
						</Stack>
						<hr/>
						<Link to="/member" className="fs-5 fw-normal text-decoration-none text-secondary">내 정보</Link>
						<hr/>
						<Link to="/member" className="fs-5 fw-normal text-decoration-none text-secondary">설정</Link>
						<hr/>
						<Button variant="outline-secondary" size="lg" onClick={onClickLogout}>Logout</Button>
					</Stack>
					</>
				):(
					<>
					<Stack direction="vertical" gap={3}>
						<Stack direction="horizontal" gap={4}>
				        	<Image src={profile} roundedCircle style={{ width: '100px', height : '100px'}}/>
				        	<div>
				        		<div className="fs-2 fw-normal text-secondary">로그인을 해주세요.</div>
				        	</div>
						</Stack>
						<Stack direction="vertical" gap={2}>
							<Button variant="primary" size="lg" onClick={()=>navigate('/login')}>Login</Button>
							<Button variant="link" size="lg" onClick={()=>navigate('/join')}>Join</Button>
						</Stack>
					</Stack>
					</>
				)}
	    	</Container>
	    	<NavBar />
    	</div>
	)
}

export default Setting;
