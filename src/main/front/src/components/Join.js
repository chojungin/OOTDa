import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Container from 'react-bootstrap/esm/Container';

import Header from './Header';

function Join() {
	
	const [account, setAccount] = useState('');
	const [accountCheck, setAccountCheck] = useState(false);
	const [userName, setName] = useState('');
	const [password, setPassword] = useState('');
	const [passwordCheck, setPasswordCheck] = useState('');
	const [year, setYear] = useState('');
	const [month, setMonth] = useState('');
	const [day, setDay] = useState('');
	
	const navigate = useNavigate();
    
    //생년월일 생성
	const years = Array.from({ length: 80 }, (_,index) => new Date().getFullYear() - index);
	const months = Array.from({ length: 12 }, (_,index) => String(index + 1).padStart(2, '0'));
	const days = Array.from({ length: 31 }, (_,index) => String(index + 1).padStart(2, '0'));
	
	//생년월일 확인
	const isBirthDate = Boolean(year && month && day);
    
    //비밀번호 일치 확인
    const isPwMatched = password === passwordCheck;
    
    //아이디 설정
    const onChangeAccount = (e) => {
		setAccount(e.currentTarget.value);
		setAccountCheck(false);
	}
    
	//아이디 중복 확인
	const onCheckDuplicate = async () => {

		if (account) {
			await axios.get('/api/auth/duplicateCheck', {
				params : { 
					account : account 
				}
			}).then((response) => {
				
				alert(response.data);
				setAccountCheck(true);
				
			}).catch((error) => {
				
				console.log("Error!! : "+error);
				setAccountCheck(false);
			});
			
		} else {
			alert("아이디를 입력해주세요.");
			setAccountCheck(false);
		}
	};
	
	//필수 입력 항목 만족 확인
	const isSatisfied = Boolean(account && accountCheck && userName && isBirthDate && isPwMatched);
    
	//회원 가입
	const onClickRegist = async (e) => {
		
		e.preventDefault();
		
		if (isSatisfied) { //필수 입력 항목 만족 확인
			
			await axios.post('/api/auth/join', {
				
				account: account,
				password: password,
				userName: userName,
				birthDate: year+month+day
				
			}).then((response) => {
				
				alert(response.data); //회원가입에 성공하였습니다.
				navigate('/login');
			  	
			}).catch((error) => {
				
				console.log("Error!! : "+error);
				alert(error.response.data); //회원가입에 실패하였습니다.
			});
			
		} else {
			alert("필수 항목을 모두 입력해주시기 바랍니다.");
		}
    }
	
	return (
		<>
		<Header />
		<Container className="py-3">
			<Form>
				<Form.Group as={Row} className="mb-3">
					<Form.Label column sm="2" xs="12">아이디</Form.Label>
					<Col sm="8" xs="9">
					    <Form.Control 
							type="text" 
							value={account} 
							onChange={onChangeAccount}
						/>
				    </Col>
				    <Col sm="2" xs="3">
						{accountCheck ? (
							  <Button variant="secondary" onClick={onCheckDuplicate}>checked</Button>
							) : (
							  <Button variant="primary" onClick={onCheckDuplicate}>check</Button>
							)}
				    </Col>
			    </Form.Group>
			    <Form.Group className="mb-3" as={Row}>
					<Form.Label column sm="2">비밀번호</Form.Label>
					<Col sm="10">
						<Form.Control 
							type="password" 
							value={password} 
							onChange={(e) => setPassword(e.target.value)}
						/>
					</Col>
				</Form.Group>
				<Form.Group className="mb-3 position-relative" as={Row}>
					<Form.Label column sm="2">비밀번호확인</Form.Label>
					<Col sm="10" className="d-flex align-items-center">
						<Form.Control 
						  type="password" 
						  value={passwordCheck}
						  onChange={(e) => setPasswordCheck(e.target.value)}
						/>
						{password && (
							isPwMatched ? (
							  <Badge pill bg="success" className="position-absolute end-0 translate-middle-x">Match!</Badge>
							) : (
							  <Badge pill bg="danger" className="position-absolute end-0 translate-middle-x">Mismatch!</Badge>
							)
						)}
					</Col>
				</Form.Group>
				<Form.Group className="mb-3" as={Row}>
					<Form.Label column sm="2">이름</Form.Label>
					<Col sm="10">
						<Form.Control 
							type="text" 
							value={userName} 
							onChange={(e) => setName(e.target.value)}
						/>
					</Col>
				</Form.Group>
				<Form.Group className="mb-3" as={Row}>
					<Form.Label column sm="2">생년월일</Form.Label>
					<Col sm="4">
						<Form.Select onChange={(e) => setYear(e.target.value)}>
							<option value="">year</option>
							{years.map((year) => (
								<option key={year} value={year}>
									{year}
								</option>
							))}
					    </Form.Select>
				    </Col>
				    <Col sm="3">
						<Form.Select onChange={(e) => setMonth(e.target.value)}>
							<option value="">month</option>
							{months.map((month) => (
								<option key={month} value={month}>
									{month}
								</option>
							))}
					    </Form.Select>
				    </Col>
				    <Col sm="3">
						<Form.Select onChange={(e) => setDay(e.target.value)}>
							<option value="">day</option>
							{days.map((day) => (
								<option key={day} value={day}>
									{day}
								</option>
							))}
					    </Form.Select>
				    </Col>
			    </Form.Group>
			    {isSatisfied? (
					<div className="d-grid gap-2">
						<Button 
							variant="primary" 
							type="submit"
							size="lg" 
							onClick={onClickRegist}
						>Join</Button>
					</div>
				):(
					<div className="d-grid gap-2">
						<Button 
							variant="secondary" 
							type="submit"
							size="lg"
							disabled
						>Join</Button>
					</div>
				)}
			</Form>
	    </Container>
	    </>
	);
}

export default Join;