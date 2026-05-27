# RoomUs - 학교 자습실 예약 관리 시스템

## 실행 방법

### Docker 실행
```bash
docker compose up --build
```

## 테스트 계정
| 역할 | 학번 | 비밀번호 |
|------|------|---------|
| ADMIN | 0000 | admin1234 |
| USER | 3404 | user1234 |
| USER | 3401 | user1234 |

## H2 콘솔
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:roomus`
- Username: `lee`
- Password: 없음

## API 테스트 순서 (Postman)
1. `POST /api/auth/login` → accessToken 획득
2. 이후 요청 Header에 `Authorization: Bearer {accessToken}` 추가

## 주요 API
| Method | URL | 설명 |
|--------|-----|------|
| POST | /api/auth/signup | 회원가입 (studentId/name/password) |
| POST | /api/auth/login | 로그인 |
| POST | /api/auth/reissue | AccessToken 재발급 |
| POST | /api/auth/logout | 로그아웃 |
| GET | /api/study-rooms | 자습실 목록 조회 |
| POST | /api/reservations | 예약 생성 |
| GET | /api/reservations | 내 예약 목록 |
| GET | /api/reservations/{id} | 예약 상세 |
| PUT | /api/reservations/{id} | 예약 수정 |
| DELETE | /api/reservations/{id} | 예약 취소 |