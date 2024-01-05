# 👔OOTDa : 외출준비를 도와주는 개인 맞춤 비서 웹/어플리케이션
<table>
  <tr>
    <th>Period</th>
    <td>2023.12.01 ~ (진행 중)</td>
  </tr>
  <tr>
    <th>OS</th>
    <td>windows10</td>
  </tr>  
  <tr>
    <th>IDE</th>
    <td>Spring Tool Suite 4</td>
  </tr> 
  <tr>
    <th>Language</th>
    <td>
      <li>Java</li>
      <li>javascript</li>
    </td>
  </tr> 
  <tr>
    <th>Summary</th>
    <td>
      위치 정보를 통해 날씨API와 대기질API를 적절히 활용하여
      기온과 기상, 대기질에 따른 외출 착장(추운 날엔 어떤 두께의 외투를 챙겨야할지 등)과
      챙겨야할 물건(우산, 양산, 마스크)과 같은 필요한 정보를 제공하고자 합니다.
    </td>
  </tr> 
  <tr>
    <th>Content</th>
    <td>
      <ul>
        <li><b>Spring boot와 React.js 연동</b></li>
        개인시간에 인터넷으로 수강하였던 Spring boot와 React 강의에 대한 실습을 위해<br>
        Spring Tool Suite 4의 작업환경을 아래와 같이 설정하였습니다.<br>
        - 스프링부트 버전 : Spring Boot 3.1.5<br>
        - 자바 버전 : Java 17 (OpenJDK Runtime Environment Corretto-17.0.8.8.1)<br>
        - 데이터베이스 : H2 database<br>
        - 빌드 도구 : Gradle<br>
        - Lombok, JPA, Spring Security, jsonwebtoken 0.11.5 ... 등<br>
        <br>
        <li><b>Spring Security와 Json Web Token을 활용한 로그인</b></li>
        로그인 시 Access Token와 Refresh Token을 부여하여 민감한 정보에 접근하거나,<br>
        확인이 필요한 기능에서 사용자를 확인하며 제한합니다. <br>
        <br>
        <li><b>사용자의 위치 정보를 활용하여 Weather API 호출</b></li>
        사용자의 현재 위치에 대한 기온을 표출하며, 기상 정보를 아이콘을 활용하여 보여주도록 합니다.<br>
        <br>
        <li><b>아이템과 온도 테이블을 생성하여 기온별 추천 아이템 표출</b></li>
        현재 위치에 대한 체감 온도 정보를 기반으로 추천 아이템을 보여주도록 합니다.<br>
        추천 착장 아이템을 착용하였는지에 대한 설문과 그에 대한 다른 사람들의 설문 결과를 볼 수 있도록 합니다.<br>
        <br>
        <li><b>🚧현재 기능 추가 중입니다!🚧</b></li>
        <br>
      </ul>
    </td>
  </tr> 
</table>

