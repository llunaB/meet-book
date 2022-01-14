### 🌈 commit convention

- feat : new feature for the user, not a new feature for build script
- fix : bug fix for the user, not a fix to a build script
- docs : changes to the documentation
- style : formatting, missing semi colons, etc; no production code change
- refactor : refactoring production code, eg. renaming a variable
- chore : updating grunt tasks etc; no production code change

    feat: profile-history schrollbar add



### ⭐️ git flow cmd

##### 1. git clone

    git clone https://lab.ssafy.com/s06-webmobile1-sub1/S06P11A206.git

##### 2. develop 브랜치로 이동

    git switch develop

👉  feature work flow

##### 3. feature branch 생성

    git branch feature/be/user/login

##### 4. feature branch 로 이동

    git switch feature/be/user/login

##### 5. ‼️중요‼️ develop branch pull 진행 

    git pull origin develop

##### 6. 개발 후 add - commit - push 진행

- 일반적으로는 develop 브랜치에 바로 push 하지만,  ssafy project에서는 진행사항 확인을 위해  feat branch 도 remote 에 올립니다.

    git add .
    git commit -m "feat: profile-history schrollbar add"
    git push origin feature/be/user/login

##### 7. merge request 진행

- assignee 와 reviewer는 필요시 등록합니다.

##### 8. develop branch에 merge 진행

- branch 가 develop 인지 확인하고 진행!
- 방법 1 : develop branch 에서feature branch 를 merge 

    # [ develop ]
    git merge feature/be/user/login

- 방법 2 : feature branch 에서 develop branch 로 push 

    # [ feature ]
    git push origin develop

##### 9. merge request 작성

- gitlab 의 푸시알림 확인
- merge request 만들기
- 내용 입력 후 delete branch after merge request 체크되어있는지 확인
- merge 


