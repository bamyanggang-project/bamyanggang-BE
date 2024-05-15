# 팀프로젝트 - bamyanggang
스프링부트 + 리액트를 활용한 Rest API 화상채팅 마피아게임 구현

<br>
<p align="center">
  <img src="https://github.com/bamyanggang-project/bamyanggang-BE/assets/151708233/742e773e-2a98-44e6-9d78-0528f8ebf3f3">
</p>

# 1. 프로젝트 소개
- 화상채팅을 이용한 마피아 게임 구현
- Websoket으로 실시간 채팅 구현.
<br>

## 2. 개발기간 
![image](https://github.com/bamyanggang-project/bamyanggang-BE/assets/151708233/e20bad70-4154-4006-9116-4c2f804233e9)


### 3. 멤버구성

<div align="center">

| **팀장 김동규** | **팀원 조하영** | **팀원 김현식** | **팀원 김솔지** |  **팀원 남영하** | 
| :------: |  :------: | :------: | :------: | :------: |

</div>
<br>


### 4. 개발 환경

#### 프론트엔드
- HTML, CSS, JavaScript, React(vite)
#### 백엔드
- Spring Boot, JDK 17, MyBatis, MySQL , JPA
#### 버전 및 이슈 관리
- GitHub + SourceTree
#### 디자인
- 피그마
<br>

### 5. 시스템 구성도

![시스템 구성도](https://github.com/bamyanggang-project/bamyanggang-BE/assets/151708233/28142b89-a9de-43a2-9e58-e3adead27e6b)


<br>

##  6. 프로젝트 구조

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂jjon
 ┃ ┃ ┃ ┗ 📂bamyanggang
 ┃ ┃ ┃ ┃ ┣ 📂community
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommunityController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommunityDao.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommunityService.java
 ┃ ┃ ┃ ┃ ┣ 📂game
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebSocketConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GameController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GameMapper.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatRepository.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GameService.java
 ┃ ┃ ┃ ┃ ┣ 📂login
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CorsMvcConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LoginController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReissueController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CustomUserDetails.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RefreshEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserEntity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂jwt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomLogoutFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtUtil.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜LoginFilter.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RefreshRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetailsService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReissueService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserService.java
 ┃ ┃ ┃ ┃ ┣ 📂mafia
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MafiaController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MafiaMapper.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MafiaService.java
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberMapper.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberService.java
 ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatDTO.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatRoom.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommunityDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MafiaRole.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MafiaRoom.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MafiaVote.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Member.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜NoticeDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ReplyDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜RoomUserInfo.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜SetRoom.java
 ┃ ┃ ┃ ┃ ┣ 📂notice
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜NoticeController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜NoticeDao.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜NoticeService.java
 ┃ ┃ ┃ ┃ ┣ 📂reply
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyContoller.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyDao.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyService.java
 ┃ ┃ ┃ ┃ ┗ 📜BamyanggangApplication.java
 ┃ ┣ 📂resources
 ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┣ 📜community.xml
 ┃ ┃ ┃ ┣ 📜game.xml
 ┃ ┃ ┃ ┣ 📜JoinMapper.xml
 ┃ ┃ ┃ ┣ 📜mafia.xml
 ┃ ┃ ┃ ┣ 📜notice.xml
 ┃ ┃ ┃ ┗ 📜reply.xml
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┣ 📜application.yml
 ┃ ┃ ┗ 📜Mybatis-Config.xml
 ┃ ┗ 📂webapp
 ┃ ┃ ┗ 📂META-INF
 ┃ ┃ ┃ ┗ 📜MANIFEST.MF
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂jjon
 ┃ ┃ ┃ ┗ 📂bamyanggang
 ┃ ┃ ┃ ┃ ┗ 📜BamyanggangApplicationTests.java

```



## 7. 역할분담 

###  🍊팀장 김동규(FE,BE)
- **UI**
  - 마이페이지 초기(회원가입, 회원수정, 로그인, 로그아웃, 회원탈퇴) 
- **기능**
  - 유효성 검사를 활용한 회원가입 (CRUD)
  - JWT 활용한 로그인,로그아웃 구현
  - AWS + nginx 배포 

###  🍊조하영(FE)

- **UI**
  - 메인페이지, 게시판, 공지사항 구현 , 마이페이지 수정(회원가입,회원수정,로그인, 로그아웃, 회원탈퇴)


  - 
###  🍊김현식(BE)

- **기능**
  - WebRtc 활용한 화상채팅 , WebSoket 활용한 실시간 채팅구현

###  🍊김솔지(FE)

- **UI**
  - 화상채팅 , 방입장,  방생성 구현

    
###  🍊남영하(BE)

- **기능**
  - 스프링부트 활용한 공지사항 게시판 , 자유게시판 구현 (CRUD)

