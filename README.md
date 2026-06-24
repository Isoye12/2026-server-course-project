# RoomUs - 학교 자습실 예약 관리 시스템

## Docker 실행
```bash
docker compose up --build
```

## 테스트 계정
| 역할 | 학번 | 비밀번호 |
|---|---|---|
| ADMIN | 0000 | admin1234 |
| USER | 3404 | user1234 |
| USER | 3401 | user1234 |

## H2 콘솔
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:roomus`
- Username: `lee`
- Password: 없음

---

## API 명세서

### 1. 회원가입

| | |
|---|---|
| 기능 | 회원가입 |
| URL | `POST /api/auth/signup` |
| Request Header | 없음 |

**Request Body**
```json
{
  "studentId": "3101",
  "name": "홍길동",
  "password": "user1234"
}
```

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "회원가입이 완료되었습니다.",
  "code": "201",
  "data": {
    "studentId": "3101",
    "name": "홍길동"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```


### 2. 로그인

| | |
|---|---|
| 기능 | 로그인 |
| URL | `POST /api/auth/login` |
| Request Header | 없음 |

**Request Body**
```json
{
  "studentId": "3404",
  "password": "user1234"
}
```

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "로그인 성공",
  "code": "200",
  "data": {
    "accessToken": "eyJhbGci...",
    "refreshToken": "eyJhbGci..."
  },
  "timestamp": "2026-05-27T14:00:00"
}
```


### 3. AccessToken 재발급

| | |
|---|---|
| 기능 | 토큰 재발급 |
| URL | `POST /api/auth/reissue` |
| Request Header | 없음 |

**Request Body**
```json
{
  "refreshToken": "eyJhbGci..."
}
```

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "토큰이 재발급되었습니다.",
  "code": "200",
  "data": {
    "accessToken": "eyJhbGci..."
  },
  "timestamp": "2026-05-27T14:00:00"
}
```


### 4. 로그아웃

| | |
|---|---|
| 기능 | 로그아웃 |
| URL | `POST /api/auth/logout` |
| Request Header | `Authorization: Bearer {accessToken}` |

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "로그아웃 되었습니다.",
  "code": "200",
  "data": null,
  "timestamp": "2026-05-27T14:00:00"
}
```


### 5. 전체 자습실 목록 조회

| | |
|---|---|
| 기능 | 전체 자습실 목록 조회 |
| URL | `GET /api/study-rooms` |
| Request Header | 없음 |

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "조회 성공",
  "code": "200",
  "data": [
    {
      "id": 1,
      "name": "면접실",
      "available": true
    },
    {
      "id": 2,
      "name": "모둠학습실",
      "available": false
    }
  ],
  "timestamp": "2026-05-27T14:00:00"
}
```


### 6. 예약 생성

| | |
|---|---|
| 기능 | 자습실 예약 |
| URL | `POST /api/reservations` |
| Request Header | `Authorization: Bearer {accessToken}` |

**Request Body**
```json
{
  "studyRoomId": 1,
  "date": "2026-05-27",
  "startPeriod": 8,
  "endPeriod": 11
}
```

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "예약이 완료되었습니다.",
  "code": "201",
  "data": {
    "reservationId": 5,
    "studyRoomId": 1,
    "studyRoomName": "면접실",
    "date": "2026-05-27",
    "startPeriod": 8,
    "endPeriod": 11,
    "status": "RESERVED"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```


### 7. 내 예약 목록 조회

| | |
|---|---|
| 기능 | 내 예약 목록 조회 |
| URL | `GET /api/reservations` |
| Request Header | `Authorization: Bearer {accessToken}` |

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "조회 성공",
  "code": "200",
  "data": [
    {
      "reservationId": 5,
      "studyRoomId": 1,
      "studyRoomName": "면접실",
      "date": "2026-05-27",
      "startPeriod": 8,
      "endPeriod": 11,
      "status": "RESERVED"
    }
  ],
  "timestamp": "2026-05-27T14:00:00"
}
```


### 8. 예약 상세 조회

| | |
|---|---|
| 기능 | 특정 예약 상세 조회 |
| URL | `GET /api/reservations/{reservationId}` |
| Request Header | `Authorization: Bearer {accessToken}` |

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "조회 성공",
  "code": "200",
  "data": {
    "reservationId": 5,
    "studyRoomId": 1,
    "studyRoomName": "면접실",
    "date": "2026-05-27",
    "startPeriod": 8,
    "endPeriod": 11,
    "status": "RESERVED"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```


### 9. 예약 수정

| | |
|---|---|
| 기능 | 예약 수정 |
| URL | `PUT /api/reservations/{reservationId}` |
| Request Header | `Authorization: Bearer {accessToken}` |

**Request Body**
```json
{
  "date": "2026-05-28",
  "startPeriod": 1,
  "endPeriod": 3
}
```

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "예약이 수정되었습니다.",
  "code": "200",
  "data": {
    "reservationId": 5,
    "studyRoomId": 1,
    "studyRoomName": "면접실",
    "date": "2026-05-28",
    "startPeriod": 1,
    "endPeriod": 3,
    "status": "RESERVED"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```


### 10. 예약 취소

| | |
|---|---|
| 기능 | 예약 취소 |
| URL | `DELETE /api/reservations/{reservationId}` |
| Request Header | `Authorization: Bearer {accessToken}` |

**Response Body**
```json
{
  "error": false,
  "success": true,
  "message": "예약이 취소되었습니다.",
  "code": "200",
  "data": null,
  "timestamp": "2026-05-27T14:00:00"
}
```

---

## 테스트 가이드 (Postman)

> 서버 실행 후 `http://localhost:8080` 기준으로 테스트합니다.  
> 로그인 이후의 모든 요청은 Header에 `Authorization: Bearer {accessToken}` 을 포함해야 합니다.

---

### 1. 회원가입

```
POST http://localhost:8080/api/auth/signup
Content-Type: application/json

{
  "studentId": "9999",
  "name": "테스트",
  "password": "test1234"
}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "회원가입이 완료되었습니다.",
  "code": "201",
  "data": {
    "studentId": "9999",
    "name": "테스트"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 2. 로그인 → 토큰 저장

```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "studentId": "3404",
  "password": "user1234"
}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "로그인 성공",
  "code": "200",
  "data": {
    "accessToken": "eyJhbGci...",
    "refreshToken": "eyJhbGci..."
  },
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 3. 자습실 목록 조회 (인증 없이)

```
GET http://localhost:8080/api/study-rooms
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "조회 성공",
  "code": "200",
  "data": [
    {
      "id": 1,
      "name": "면접실",
      "available": false
    },
    {
      "id": 2,
      "name": "모둠학습실",
      "available": true
    },
    {
      "id": 3,
      "name": "프로젝트 1실",
      "available": true
    },
    {
      "id": 4,
      "name": "프로젝트 2실",
      "available": true
    }
  ],
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 4. 예약 생성

```
POST http://localhost:8080/api/reservations
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "studyRoomId": 1,
  "date": "2026-05-28",
  "startPeriod": 1,
  "endPeriod": 3
}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "예약이 완료되었습니다.",
  "code": "201",
  "data": {
    "reservationId": 3,
    "studyRoomId": 1,
    "studyRoomName": "면접실",
    "date": "2026-05-28",
    "startPeriod": 1,
    "endPeriod": 3,
    "status": "RESERVED"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 5. 내 예약 목록 조회

```
GET http://localhost:8080/api/reservations
Authorization: Bearer {accessToken}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "조회 성공",
  "code": "200",
  "data": [
    {
      "reservationId": 3,
      "studyRoomId": 1,
      "studyRoomName": "면접실",
      "date": "2026-05-28",
      "startPeriod": 1,
      "endPeriod": 3,
      "status": "RESERVED"
    },
    {
      "reservationId": 1,
      "studyRoomId": 1,
      "studyRoomName": "면접실",
      "date": "2026-05-28",
      "startPeriod": 7,
      "endPeriod": 9,
      "status": "RESERVED"
    }
  ],
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 6. 예약 상세 조회

```
GET http://localhost:8080/api/reservations/1
Authorization: Bearer {accessToken}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "조회 성공",
  "code": "200",
  "data": {
    "reservationId": 1,
    "studyRoomId": 1,
    "studyRoomName": "면접실",
    "date": "2026-05-28",
    "startPeriod": 7,
    "endPeriod": 9,
    "status": "RESERVED"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 7. 예약 수정

```
PUT http://localhost:8080/api/reservations/1
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "date": "2026-05-29",
  "startPeriod": 5,
  "endPeriod": 7
}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "예약이 수정되었습니다.",
  "code": "200",
  "data": {
    "reservationId": 1,
    "studyRoomId": 1,
    "studyRoomName": "면접실",
    "date": "2026-05-29",
    "startPeriod": 5,
    "endPeriod": 7,
    "status": "RESERVED"
  },
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 8. 예약 취소

```
DELETE http://localhost:8080/api/reservations/1
Authorization: Bearer {accessToken}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "예약이 취소되었습니다.",
  "code": "200",
  "data": null,
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 9. AccessToken 재발급

```
POST http://localhost:8080/api/auth/reissue
Content-Type: application/json

{
  "refreshToken": "eyJhbGci..."
}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "토큰이 재발급되었습니다.",
  "code": "200",
  "data": {
    "accessToken": "eyJhbGci..."
  },
  "timestamp": "2026-05-27T14:00:00"
}
```

---

### 10. 로그아웃

```
POST http://localhost:8080/api/auth/logout
Authorization: Bearer {accessToken}
```

**기대 응답**
```json
{
  "error": false,
  "success": true,
  "message": "로그아웃 되었습니다.",
  "code": "200",
  "data": null,
  "timestamp": "2026-05-27T14:00:00"
}
```
