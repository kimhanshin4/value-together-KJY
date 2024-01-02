﻿# 👑 Value Together (2023.12.26 ~ 2024.1.3)
## 목차
[1. 프로젝트 소개](#프로젝트-소개) <br>
[2. 팀원 소개](#팀원-소개) <br>
[3. 기술 스택](#기술-스택) <br>
[4. 협업 전략](#협업-전략) <br>
[5. ERD](#ERD) <br>
[6. API 명세](#API-명세) <br>
[7. 프로젝트 주요 기능](#프로젝트-주요-기능) <br>
[8. 테스트](#테스트)

## 프로젝트 소개
협업을 위한 가치있는 서비스를 제공하는 칸반보드 툴 구현 프로젝트입니다.

## 팀원 소개
| <img src ="https://avatars.githubusercontent.com/u/99391320?v=4" width="160px" height="160px"> | <img src ="https://avatars.githubusercontent.com/u/75934088?v=4" width="160px" height="160px"> | <img src ="https://avatars.githubusercontent.com/u/60142959?v=4" width="160px" height="160px"> | <img src ="https://avatars.githubusercontent.com/u/55584664?v=4" width="160px" height="160px"> | <img src ="https://avatars.githubusercontent.com/u/109781694?v=4" width="160px" height="160px"> |
|:---------------------------------:|:---------------------------------:|:----------------------------------:|:--------------------------------:|:----------------------------------:|
|이예진<br>[@dlwls423](https://github.com/dlwls423)|최준영<br>[@junxtar](https://github.com/junxtar)|강용수<br>[@emost22](https://github.com/emost22)|김재윤<br>[@lycoris62](https://github.com/lycoris62)|용소희<br>[@yongcowhee](https://github.com/yongcowhee)|
|-회원가입<br>-mail<br>-프로필 기능<br>-댓글 기능| -카드 기능<br>-소셜 로그인: 깃허브, <br>네이버, 구글|-체크리스트 기능<br>-할일 기능<br>-배포<br>-RestDocs|-로컬 로그인, 로그아웃<br>-인증/인가<br>-작업자 기능|-팀 기능<br>-카테고리 기능<br>-전역 예외 처리|

## 기술 스택
### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- OAuth 2
- Spring Security 6.2.0
- Mail
### DB
- MySQL 8.0.33
- Redis 6.0.16
- H2DB 2.1.214 (Test용)
### Infra
- AWS
    - EC2
    - S3
    - CloudFront
    - ACM
    - RDS
    - Route53
### Docs
- RestDocs
- Jacoco

## 협업 전략
### Git flow
- `main`
- `develop` (default)
- `feature/기능`
### Commit Convention
- `Feat` : 새로운 기능을 추가하는 경우
- `Fix` : 버그를 고친경우
- `Docs` : 문서를 수정한 경우
- `Style` : 코드 포맷 변경, 세미콜론 누락, 코드 수정이 없는경우
- `Refactor` : 코드 리팩토링
- `Test`: 테스트 코드 작성
### Issue, PR Convention
- Issue, PR 템플릿 사용
- 커스텀한 라벨 사용
- PR마다 관련 이슈를 생성
### Code Style
- Spotless 적용
### Code Convention
- Entity: `setter`와 `method` 사용 x, `private Builder` 사용
- 객체 간 변환: `org.mapstruct.Mapper` 사용
- api 1개마다 req, res dto 1개씩 사용
- Custom `ResultCode`, `ResponseEntity` 사용
- `Optional` 제외: validator file로 검증 로직을 분리
- Service 인터페이스와 impl로 분리
- Repository 기능을 제한: `@RepositoryDefinition`
- 네이밍 규칙
    - dto: 도메인 + 기능 + Req/Res
    - Entity: 도메인명 그대로
    - 테이블명: tb_도메인명
    - id: 도메인명 + id
  
## ERD
<img src="https://file.notion.so/f/f/83c75a39-3aba-4ba4-a792-7aefe4b07895/f53e300f-8b8a-42af-8cb3-f1d2fc0299a9/erd.png?id=3b195ece-5cb5-4a7f-91d6-cb96504d92a3&table=block&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&expirationTimestamp=1704276000000&signature=-CsN6qGS-C_u4V0Q3j92hVroYJ7tlhXF5v22mWy2rKI&downloadName=erd.png" width="700px" height="500px">

## API 명세
[API 명세서](https://teamsparta.notion.site/API-6c31cb6ffb6647bc8e935f6bf4fc6a17)

## 프로젝트 주요 기능
### [회원가입 & 로그인 및 로그아웃]
🔎 회원가입
- `이메일`, `닉네임`, `비밀번호`는 필수 값이다.
- `이메일 인증이 필요`하다.
- `닉네임은 고유한 값`이다.

🔎 로컬 로그인
- `닉네임` + `비밀번호`로 로그인을 할 수 있다.
- 로컬 로그인 내에서 `이메일 중복`을 허용하지 않는다.
  
🔎 소셜 로그인
- `github`, `google`, `naver` 총 3가지 방법이 있다.
- 로컬과 소셜 로그인에서 `이메일 중복`을 허용한다.

🔎 로그아웃
- `로그아웃`을 할 수 있다.

### [프로필 기능]
🔎 프로필 수정
- `닉네임` 중복 확인을 할 수 있다.
- `기존 비밀번호`를 입력하여 일치하는지 확인할 수 있다.
- `닉네임`, `비밀번호`, `자기소개`, `프로필 이미지`를 변경할 수 있다.

🔎 프로필 조회
- `유저Id`, `닉네임`, `이메일`, `자기소개`, `프로필 이미지`를 조회할 수 있다.

### [팀 기능]
🔎 팀 생성
- `팀명`, `설명`, `배경색`을 입력하여 팀을 생성할 수 있다.
- 팀을 생성한 사용자는 `팀장`이 된다.

🔎 팀 조회
- `팀명`, `설명`, `배경색`, `팀원`을 조회할 수 있다.
 
🔎 팀 수정
- `팀장`은 팀의 `팀명`, `설명`, `배경색`을 수정할 수 있다.

🔎 팀 삭제
- `팀장`은 팀을 삭제할 수 있다.
- soft delete를 적용한다.

🔎 팀 복구
- `팀장`은 삭제한 팀을 복구할 수 있다.

🔎 팀원 초대
- `팀장`은 `닉네임`으로 팀에 사용자를 `팀원`으로 초대할 수 있다.
- 팀에 초대하면 해당 사용자에게 `이메일`이 발송된다.
- 사용자는 이메일에서 `초대 링크`를 클릭하여 초대를 수락할 수 있다.

🔎 팀원 삭제
- `팀장`은 `닉네임`으로 팀원를 삭제할 수 있다.

### [카테고리 기능]
🔎 카테고리 생성
- `팀장 및 팀원`은 `카테고리명`을 입력하여 카테고리를 생성할 수 있다.
- `순서`를 보장하는 필드 값이 존재한다.

🔎 카테고리 전체 조회
- `팀장 및 팀원`은 팀에 생성된 모든 카테고리를 `삭제 여부`를 조건으로 조회할 수 있다.
- 카테고리에 등록된 `카드`들도 함께 조회할 수 있다.

🔎 카테고리 수정
- `팀장 및 팀원`은 `카테고리명`을 수정할 수 있다.
- `팀장 및 팀원`은 카테고리의 `순서`를 변경할 수 있다.

🔎 카테고리 삭제
- `팀장 및 팀원`은 카테고리를 삭제할 수 있다.
- soft delete를 적용한다.

🔎 카테고리 복구
- `팀장 및 팀원`은 삭제한 카테고리를 복구할 수 있다.


### [카드 기능]
🔎 카드 생성
- `팀장 및 팀원`은 특정 카테고리에 `제목`, `설명`, `마감일`, `첨부 파일`을 입력하여 카드를 생성할 수 있다.
- 카테고리 내에서 카드의 `순서`를 보장하는 필드 값이 존재한다.

🔎 카드 단건 조회
- `팀장 및 팀원`은 카드를 단건 조회할 수 있다.
- 카드에 등록된 `체크리스트`, `할일`, `댓글`들도 함께 조회할 수 있다.

🔎 카드 수정
- `팀장 및 팀원`은 `제목`, `설명`, `마감일`, `첨부 파일`을 수정할 수 있다.
- `팀장 및 팀원`은 같은 카테고리 내에서 카드의 `순서`를 변경할 수 있다.
- `팀장 및 팀원`은 카드를 다른 카테고리로 이동할 수 있다.

🔎 카드 삭제
- `팀장 및 팀원`은 카드를 삭제할 수 있다.

### [담당자 기능]
🔎 담당자 등록
- `팀장 및 팀원`은 `닉네임`을 통해 카드에 `담당자`를 등록할 수 있다.

🔎 담당자 삭제
- `팀장 및 팀원`은 `닉네임`을 통해 카드의 `담당자`를 삭제할 수 있다.

### [체크리스트 기능]
🔎 체크리스트 생성
- `팀장 및 팀원`은 카드에 `제목`을 입력하여 체크리스트를 등록할 수 있다.

🔎 체크리스트 수정
- `팀장 및 팀원`은 체크리스트의 `제목`을 수정할 수 있다.

🔎 체크리스트 삭제
- `팀장 및 팀원`은 체크리스트를 삭제할 수 있다.

### [할일 기능]
🔎 할일 생성
- `팀장 및 팀원`은 체크리스트에 `내용`을 입력하여 할일을 등록할 수 있다.

🔎 할일 수정
- `팀장 및 팀원`은 할일의 `내용`을 수정할 수 있다.

🔎 할일 삭제
- `팀장 및 팀원`은 할일을 삭제할 수 있다.

### [댓글 기능]
🔎 댓글 생성
- `팀장 및 팀원`은 카드에 댓글을 입력할 수 있다.

🔎 댓글 수정
- `팀장 및 팀원`은 댓글을 수정할 수 있다.

🔎 댓글 삭제
- `팀장 및 팀원`은 댓글을 삭제할 수 있다.

## 테스트
### Jacoco 테스트 문서
<img src="https://file.notion.so/f/f/83c75a39-3aba-4ba4-a792-7aefe4b07895/ff24bfc2-800f-408f-ab4c-c566dc3be595/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-01-02_%EC%98%A4%ED%9B%84_7.30.05.png?id=7d50d89d-fd3f-434c-a890-d807195aee7c&table=block&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&expirationTimestamp=1704283200000&signature=kvWDTRa_hRDhZPuMblDF6EHvuF4EPAMhGsO5STlGAdA&downloadName=%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7+2024-01-02+%EC%98%A4%ED%9B%84+7.30.05.png" width="700px" height="1000px">
