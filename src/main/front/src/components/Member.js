import { useEffect, useState } from 'react';
import { getMember } from '../api/TokenAPI';

import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Container from 'react-bootstrap/esm/Container';

function Member() {
	
	const [loading, setLoading] = useState(false);
	const [member, setMember] = useState({});
	
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
	
	useEffect(() => {
		
		setMember(getMember());
		setLoading(true);
		
		console.log(loading + " \n " + member);
		
	},[]);
	
	return (
		<Container className="py-5">
			<Form>
				<InputGroup className="mb-3" as={Row}>
					<Form.Label column sm="2">아이디</Form.Label>
					<Col sm="10">
					    <Form.Control 
							type="text"
							value={member.account}
							readOnly
						/>
				    </Col>
			    </InputGroup>
			    <Form.Group className="mb-3" as={Row}>
					<Form.Label column sm="2">비밀번호</Form.Label>
					<Col sm="10">
						<Form.Control 
							type="password" 
							placeholder="password"
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
						  placeholder="password check"
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
							value={member.userName} 
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
			</Form>
	    </Container>
	);
	
}

export default Member;