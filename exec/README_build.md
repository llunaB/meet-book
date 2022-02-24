# 빌드 및 배포
현재 빌드 파일은 GitLab `/builds` 에 업로드되어 있습니다.



## 백엔드
### 스택
- Swagger 2.9.2
- Spring Boot 2.6.2
- Spring Security
- Spring Scheduler
- MySQL: 8.0.28-0ubuntu0.20.04.3

### 빌드
- jar 파일 빌드
`./gradlew build`

### 실행
- ec2 탑재 위치: /home/ubuntu/server
- 백그라운드로 실행
`nohup java -jar -Duser.timezone=Asia/Seoul <jar 파일명> &`



## 프론트엔드
### 스택
- Vue: 2.6.11
- Vue / CLI: 4.5.8
- Vuex: 3.4.0
- Vueautify: 2.4.0
- npm: 6.14.10
- axios: 0.24.0

### 빌드
- client 폴더에서 환경변수 확인 (env.production)
- 빌드
`npm i`
`npm run build`
- nginx 상태 확인 (종료 후 작업하는 것이 안전)
`sudo service nginx stop`
- 기존 빌드 결과물 삭제 혹은 백업
- 탑재 위치: `/home/ubuntu/client/dist/`
- 삭제 `rm -r dist/`
- 빌드 결과물 (dist) 원격 서버에 탑재
- nginx 재가동
`sudo service nginx start`
`systemctl nginx status`



## 화상회의: OpenVidu
### 빌드
- OpenVidu는 기본적으로 Docker Compose를 통해 작동하므로, Docker와 Docker Compose를 먼저 설치하여야 합니다.
- (Superuser 권한인 상태에서) `cd /opt` (권장 설치위치로 이동)
- `curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash` 실행

### 실행
- 실행 위치 `/opt/openvidu` (권장 설치위치임)
- OpenVidu 시작 `./openvidu start`
- `OpenVidu is ready` 문구를 보고 `Ctrl+C`로 터미널로 빠져나오면 백그라운드에서 실행되고 있음
- 백그라운드의 OpenVidu 종료 `./openvidu stop`

### 설정
- OpenVidu의 상세 설정을 위한 환경변수 파일: `/opt/openvidu/.env`
-- 특히 인증서 관련 설정을 제대로 작성해야 합니다.
-- 주요 설정값
--- `DOMAIN_OR_PUBLIC_IP`: 서비스 주소
--- `OPENVIDU_SECRET`: OpenVidu 서비스가 관리될 Secret Key
--- `CERTIFICATE_TYPE` & `LETSENCRYPT_EMAIL`: 인증서 관련 설정값
- `docker-compose.override.yml` & `docker-compose.yml`
-- OpenVidu 동작에 필요한 각 Container에 대한 설정을 직접 입력할 수 있음
-- 각 Container들이 서로 긴밀하게 연결되어 동작하는 만큼 설정에 주의해야 함



## 주요 계정 및 프로퍼티
- MySql 계정
-- ID: ssafyA206
-- PW: sAs2a0f6y
