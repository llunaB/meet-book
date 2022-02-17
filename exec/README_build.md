# 빌드 및 배포

## 백엔드
### 스택
- JVM
- 웹서버 : Nginx
- IDE 버젼 

### 빌드
- jar 파일 빌드
`./gradlew build`

### 실행
- 백그라운드로 실행
`nohup java -jar -Duser.timezone=Asia/Seoul <jar 파일명> &`

## 프론트엔드
### 스택
- Vue 2
- Vuetify

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

### 실행
- 실행 위치 `/opt/openvidu`
- OpenVidu 시작 `./openvidu start`
- `OpenVidu is ready` 문구를 보고 `Ctrl+C`로 터미널로 빠져나오면 백그라운드에서 실행되고 있음
- 백그라운드의 OpenVidu 종료 `./openvidu stop`


## 주요 계정 및 프로퍼티
- MySql 계정