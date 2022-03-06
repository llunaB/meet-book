
## 실시간 화상 독서모임 서비스 밋북(MeetBook)
### 개발기간
2022.01.10 ~ 02.18

### 핵심 기능
- 도서 기반으로 화상모임 개최
- 질문과 답변 통한 모임 접속
- 회의 북마크 통한 사전 알림

### 모임 설정기능
- 도서 설정 기능
- 모임 개최시간 설정 기능
- 인원 설정 기능

### 화상 모임기능
- 화상통화
- 실시간 채팅
- 귓속말 및 유저강퇴기능

### 기술 스택
##### Frontend
- Vue: 2.6.11
- Vue / CLI: 4.5.8
- Vuex: 3.4.0
- Vueautify: 2.4.0
- npm: 6.14.10
- axios: 0.24.0

##### Backend
- Swagger 2.9.2
- Spring Boot 2.6.2
- Spring Security
- Spring Scheduler
- MySQL: 8.0.28
- NGINX
- Jenkins
- AWS EC2(Ubuntu 20.04 LTS)

##### ETC
- Json Web Token
- GitLab
- Jira
- Webex
- Openvidu

### 서버 아키텍쳐
<img src="https://user-images.githubusercontent.com/78768769/156929819-aff7c17f-b082-4f2a-9135-a443d56ea6ea.png" width="600">


### 🌈 commit convention

- `feat` : new feature for the user, not a new feature for build script
- `fix` : bug fix for the user, not a fix to a build script
- `docs` : changes to the documentation
- `style` : formatting, missing semi colons, etc; no production code change
- `refactor` : refactoring production code, eg. renaming a variable
- `chore` : updating grunt tasks etc; no production code change

```bash
feat: profile-history schrollbar add
```


