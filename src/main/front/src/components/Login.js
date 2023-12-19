import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/esm/Container';

function Login() {
	
	const [account, setAccount] = useState('');
	const [password, setPassword] = useState('');
	
	const navigate = useNavigate();
	
    //로그인
    const onClickLogin = async (e) => {
		
		e.preventDefault();
		
		if (account && password){ //필수 입력 항목 만족 확인
		
			await axios.post(`/api/auth/login`, {
				
				account : account,
				password : password
				
			}).then((response) => {
				
				alert(response.data.message); //"로그인에 성공하였습니다."
  				
  				//로그인 성공 시, 로컬스토리지에 데이터 저장
  				localStorage.clear();
  				localStorage.setItem("tokenType", response.data.tokenType);
  				localStorage.setItem("accessToken", response.data.accessToken);
  				localStorage.setItem("refreshToken", response.data.refreshToken);
  				localStorage.setItem("userName", response.data.userName);
  				localStorage.setItem("roleType", response.data.roleType);
	            
	            navigate('/');
	            
		        }).catch((error) => {

					console.log("Error!! : " + error);
		        	alert(error.response.data.message);
		        	
				});
		        
		} else if (!account){
			alert("아이디를 입력하세요.");
		} else if (!password){
			alert("비밀번호를 입력하세요.");
		} 
	}
	
  return (
    <Container className="py-5">
    	<h1>Login</h1>
    	<Form>
    		<Form.Group className="mb-3" as={Row}>
    			<Form.Label column sm="2">아이디</Form.Label>
				<Col sm="10">
					<Form.Control 
						type="text" 
						placeholder="Please enter your id."
						value={account} 
						onChange={(e) => setAccount(e.currentTarget.value)}
					/>
				</Col>
			</Form.Group>
			<Form.Group className="mb-3" as={Row}>
				<Form.Label column sm="2">비밀번호</Form.Label>
				<Col sm="10">
					<Form.Control 
						type="password" 
						placeholder="Please enter your password."
						value={password} 
						onChange={(e) => setPassword(e.target.value)}
					/>
				</Col>
			</Form.Group>
			{account && password? (
				<div className="d-grid gap-2">
					<Button 
						variant="primary" 
						type="submit"
						size="lg" 
						onClick={onClickLogin}
					>Login</Button>
				</div>
			):(
				<div className="d-grid gap-2">
					<Button 
						variant="secondary" 
						type="submit"
						size="lg" 
						disabled
					>Login</Button>
				</div>
			)}
    	</Form>
    </Container>
  );
}

export default Login;
