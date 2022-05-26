
## 실시간 화상 독서모임 서비스 밋북(MeetBook)
### 개발기간
2022.01.10 ~ 02.18

### 서비스 UCC
https://www.youtube.com/watch?v=ssAgi1jPhxo

### Notion 경로
https://hilarious-belief-670.notion.site/afb96acfc684422ba883802cc2c4414a

### 최종 발표자료
[https://shorturl.at/foDY8](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/d81e47a4-1ac6-455b-a8ca-f0820b1ae778/%E1%84%89%E1%85%A5%E1%84%8B%E1%85%AE%E1%86%AF_2%E1%84%87%E1%85%A1%E1%86%AB_A206_%E1%84%87%E1%85%A1%E1%86%AF%E1%84%91%E1%85%AD%E1%84%8C%E1%85%A1%E1%84%85%E1%85%AD.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220526%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220526T124122Z&X-Amz-Expires=86400&X-Amz-Signature=83f8639c3f4a5326a92d60c3386b51532eb1b54d2133ea2b94540cc6a7717b1a&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25E1%2584%2589%25E1%2585%25A5%25E1%2584%258B%25E1%2585%25AE%25E1%2586%25AF_2%25E1%2584%2587%25E1%2585%25A1%25E1%2586%25AB_A206_%25E1%2584%2587%25E1%2585%25A1%25E1%2586%25AF%25E1%2584%2591%25E1%2585%25AD%25E1%2584%258C%25E1%2585%25A1%25E1%2584%2585%25E1%2585%25AD.pdf%22&x-id=GetObject)

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
<img width="600" alt="architecture" src="https://user-images.githubusercontent.com/78768769/156929937-71710193-5bb8-4fc5-9463-970664af0578.png">


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


