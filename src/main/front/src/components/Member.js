import { useEffect, useState } from 'react';
import { getMember } from '../api/TokenAPI';

import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Container from 'react-bootstrap/esm/Container';

import Header from './Header';

function Member() {

	const [loading, setLoading] = useState(false); //로딩
	
	const [account, setAccount] = useState('');
	const [userName, setName] = useState('');
	const [password, setPassword] = useState('');
	const [passwordCheck, setPasswordCheck] = useState('');
	const [year, setYear] = useState('');
	const [month, setMonth] = useState('');
	const [day, setDay] = useState('');
	
	//생년월일 생성
	const years = Array.from({ length: 80 }, (_,index) => new Date().getFullYear() - index);
	const months = Array.from({ length: 12 }, (_,index) => String(index + 1).padStart(2, '0'));
	const days = Array.from({ length: 31 }, (_,index) => String(index + 1).padStart(2, '0'));
	
	//비밀번호 일치 확인
    const isPwMatched = password === passwordCheck;
    
    //생년월일 확인
	const isBirthDate = Boolean(year && month && day);
    
    //필수 입력 항목 만족 확인
	const isSatisfied = Boolean(userName && isBirthDate && isPwMatched);
	
	const FetchData = async () => {
		
		try {
			
			const userInfo = await getMember();
			setAccount(userInfo.account);
			setName(userInfo.userName);
			setYear((userInfo.birthDate).substring(0,4));
			setMonth((userInfo.birthDate).substring(4,6));
			setDay((userInfo.birthDate).substring(6,8));
			
			setLoading(true);
			
		} catch (error) {
			console.log(error);
		}
	}
	
	useEffect(() => {
		FetchData();
	},[]);
	
	if (!loading){
		return (
			<>
			<Header />
			<Container className="py-5">
				Loading....
			</Container>
			</>
		);
	}
	
	return (
		<>
		<Header />
		<Container className="py-5">
			<Form>
				<Form.Group className="mb-3" as={Row}>
					<Form.Label column sm="2">아이디</Form.Label>
					<Col sm="10">
					    <Form.Control 
							type="text"
							value={account}
							disabled
						/>
				    </Col>
			    </Form.Group>
			    <Form.Group className="mb-3" as={Row}>
					<Form.Label column sm="2">비밀번호 변경</Form.Label>
					<Col sm="10">
						<Form.Control 
							type="password" 
							value={password}
							onChange={(e) => setPassword(e.target.value)}
						/>
					</Col>
				</Form.Group>
				<Form.Group className="mb-3 position-relative" as={Row}>
					<Form.Label column sm="2">비밀번호 변경확인</Form.Label>
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
							placeholder="name"
							value={userName}
							onChange={(e) => setName(e.target.value)}
						/>
					</Col>
				</Form.Group>
				<Form.Group className="mb-3" as={Row}>
					<Form.Label column sm="2">생년월일</Form.Label>
					<Col sm="4">
						<Form.Select value={year} onChange={(e) => setYear(e.target.value)}>
							<option value="">year</option>
							{years.map((option, index) => (
								<option key={index} value={option}>
									{option}년
								</option>
							))}
					    </Form.Select>
				    </Col>
				    <Col sm="3">
						<Form.Select value={month} onChange={(e) => setMonth(e.target.value)}>
							<option value="">month</option>
							{months.map((option, index) => (
								<option key={index} value={option}>
									{option}월
								</option>
							))}
					    </Form.Select>
				    </Col>
				    <Col sm="3">
						<Form.Select value={day} onChange={(e) => setDay(e.target.value)}>
							<option value="">day</option>
							{days.map((option, index) => (
								<option key={index} value={option}>
									{option}일
								</option>
							))}
					    </Form.Select>
				    </Col>
			    </Form.Group>
			    <div className="d-grid mt-5">
			    {isSatisfied? (
					<Button 
						variant="primary" 
						type="submit"
						size="lg" 
					>Save</Button>
				):(
					<Button 
						variant="secondary" 
						type="submit"
						size="lg"
						disabled
					>Save</Button>
				)}
				<Button variant="link" size="lg">탈퇴</Button>
				</div>
			</Form>
	    </Container>
	    </>
	);
	
}

export default Member;