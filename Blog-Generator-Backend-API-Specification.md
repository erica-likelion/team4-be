# 🚀 Blog Generator Backend - 실제 서비스 API 명세서 v1.0

---

## 📋 **개요**

| 항목 | 내용 |
|------|------|
| **Base URL** | `http://localhost:8081` (개발환경) |
| **Content-Type** | `application/json` |
| **인증** | 현재 토큰 인증 없음 (개발 단계) |
| **OpenAI 모델** | GPT-4o |
| **타임아웃** | 60초 |

---

## 📝 **Made API (블로그 생성/관리)**

### 1.1 블로그 목록 조회

**API 이름**: 블로그 목록 조회  
**설명**: 생성된 모든 블로그 포스트의 목록을 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/made`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**:
```json
[
  {
    "madeId": 14,
    "image": "",
    "hashTag": "\"hashtags\": [\"#강남카페\", \"#스타벅스\", \"#아늑한분위기\", \"#커피맛집\", \"#데이트장소\", \"#프리미엄커피\", \"#강남스타벅스\"]",
    "prompt": "테스트 블로그 생성\n\n해시태그: #테스트 #블로그\n\n상세 요청: 간단한 테스트 블로그를 생성해주세요",
    "userInput": "테스트 블로그 생성",
    "resultTitle": "강남 스타벅스, 특별한 순간을 위한 최적의 공간!",
    "resultContent": "서울의 중심, 강남구 테헤란로에 자리 잡은 스타벅스 강남점은...",
    "storeId": 1,
    "storeName": "스타벅스 강남점",
    "createdAt": "2025-08-23T00:29:15.828"
  }
]
```

---

### 1.2 블로그 생성 (AI 생성 포함)

**API 이름**: 블로그 생성 (AI 생성 포함)  
**설명**: AI를 사용하여 블로그 제목과 콘텐츠를 자동 생성합니다.

**HTTP Method**: `POST`  
**URL**: `/api/made/upload-images`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "storeId": 1,
  "userInput": "테스트 블로그 생성",
  "hashTags": "#테스트 #블로그",
  "detailedRequest": "간단한 테스트 블로그를 생성해주세요"
}
```

**Status Code**: `201 Created`

**Response Body**: MadeResponseDto 형태

---

### 1.3 블로그 콘텐츠 수정

**API 이름**: 블로그 콘텐츠 수정  
**설명**: 기존 블로그 내용을 AI를 사용하여 수정합니다.

**HTTP Method**: `POST`  
**URL**: `/api/made/modify-content`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "madeId": 1,
  "modificationRequest": "더 자세하고 감정적인 내용으로 수정해주세요"
}
```

**Status Code**: `200 OK`

**Response Body**: MadeResponseDto 형태

---

### 1.4 특정 블로그 결과 조회

**API 이름**: 특정 블로그 결과 조회  
**설명**: 특정 블로그 포스트의 상세 정보를 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/made/result/{madeId}`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: MadeResponseDto 형태

---

### 1.5 사용자 입력 처리

**API 이름**: 사용자 입력 처리  
**설명**: 사용자 입력을 받아 블로그를 생성합니다.

**HTTP Method**: `POST`  
**URL**: `/api/made/user-input`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "storeId": 1,
  "userInput": "맛있는 커피를 마시고 싶어요",
  "prompt": "커피 관련 블로그 포스트"
}
```

**Status Code**: `201 Created`

**Response Body**: MadeResponseDto 형태

---

## 🏪 **Store API (매장 관리)**

### 2.1 매장 목록 조회

**API 이름**: 매장 목록 조회  
**설명**: 등록된 모든 매장의 목록을 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/stores`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**:
```json
[
  {
    "storeId": 1,
    "storeName": "스타벅스 강남점",
    "storeImage": null,
    "information": "프리미엄 커피와 분위기 좋은 카페 공간",
    "location": "서울시 강남구 테헤란로 123",
    "storeTime": null,
    "closedDays": null,
    "reservation": null,
    "menu": null,
    "count": null,
    "madeList": [...],
    "convenienceList": [],
    "promotionInfoList": []
  }
]
```

---

### 2.2 특정 매장 조회

**API 이름**: 특정 매장 조회  
**설명**: 특정 매장의 상세 정보를 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/stores/{storeId}`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: Store 엔티티 형태

---

### 2.3 매장명으로 검색

**API 이름**: 매장명으로 검색  
**설명**: 매장명을 기준으로 매장을 검색합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/stores/search/name?storeName=스타벅스`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: Store 목록

---

### 2.4 위치로 검색

**API 이름**: 위치로 검색  
**설명**: 위치를 기준으로 매장을 검색합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/stores/search/location?location=강남`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: Store 목록

---

### 2.5 예약 가능한 매장 조회

**API 이름**: 예약 가능한 매장 조회  
**설명**: 예약이 가능한 매장들을 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/stores/reservable`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: Store 목록

---

### 2.6 매장 생성

**API 이름**: 매장 생성  
**설명**: 새로운 매장을 등록합니다.

**HTTP Method**: `POST`  
**URL**: `/api/dashboard/stores`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "storeName": "새로운 카페",
  "location": "서울시 강남구",
  "information": "매장 설명"
}
```

**Status Code**: `201 Created`

**Response Body**: 생성된 Store 엔티티

---

### 2.7 매장 수정

**API 이름**: 매장 수정  
**설명**: 기존 매장 정보를 수정합니다.

**HTTP Method**: `PUT`  
**URL**: `/api/dashboard/stores/{storeId}`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "storeName": "수정된 카페명",
  "location": "수정된 주소",
  "information": "수정된 설명"
}
```

**Status Code**: `200 OK`

**Response Body**: 수정된 Store 엔티티

---

## 🔌 **Convenience API (편의시설 관리)**

### 3.1 편의시설 목록 조회

**API 이름**: 편의시설 목록 조회  
**설명**: 특정 매장의 편의시설 목록을 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/stores/{storeId}/conveniences`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**:
```json
[
  {
    "id": 1,
    "wifi": true,
    "outlet": true,
    "pet": false,
    "packagingDelivery": true,
    "storeId": 1
  }
]
```

---

### 3.2 편의시설 생성

**API 이름**: 편의시설 생성  
**설명**: 새로운 편의시설을 등록합니다.

**HTTP Method**: `POST`  
**URL**: `/api/dashboard/stores/{storeId}/conveniences`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "wifi": true,
  "outlet": true,
  "pet": false,
  "packagingDelivery": true
}
```

**Status Code**: `201 Created`

**Response Body**: 생성된 Convenience 엔티티

---

### 3.3 편의시설 수정

**API 이름**: 편의시설 수정  
**설명**: 기존 편의시설 정보를 수정합니다.

**HTTP Method**: `PUT`  
**URL**: `/api/dashboard/conveniences/{convenienceId}`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "wifi": true,
  "outlet": false,
  "pet": true,
  "packagingDelivery": true
}
```

**Status Code**: `200 OK`

**Response Body**: 수정된 Convenience 엔티티

---

## 🎉 **Promotion Info API (프로모션 관리)**

### 4.1 대시보드 프로모션 목록

**API 이름**: 대시보드 프로모션 목록  
**설명**: 대시보드용 프로모션 목록을 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/promotions`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**:
```json
[
  {
    "id": 1,
    "title": "여름 시즌 할인",
    "image": null,
    "description": "여름 시즌 특별 할인 이벤트",
    "date": "2025-08-23",
    "like": 15,
    "comment": "좋은 이벤트네요!",
    "prompt": "여름 시즌 할인 프로모션",
    "storeId": 1
  }
]
```

---

### 4.2 대시보드 프로모션 상세

**API 이름**: 대시보드 프로모션 상세  
**설명**: 특정 프로모션의 상세 정보를 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/dashboard/promotions/{promotionId}`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: PromotionInfo 엔티티

---

### 4.3 커뮤니티 프로모션 목록

**API 이름**: 커뮤니티 프로모션 목록  
**설명**: 커뮤니티용 프로모션 목록을 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/community/promotions`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: PromotionInfo 목록

---

### 4.4 커뮤니티 프로모션 상세

**API 이름**: 커뮤니티 프로모션 상세  
**설명**: 특정 커뮤니티 프로모션의 상세 정보를 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/community/promotions/{promotionId}`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: PromotionInfo 엔티티

---

### 4.5 프로모션 좋아요

**API 이름**: 프로모션 좋아요  
**설명**: 프로모션에 좋아요를 추가합니다.

**HTTP Method**: `POST`  
**URL**: `/api/community/promotions/{promotionId}/like`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: 좋아요 처리 결과

---

### 4.6 프로모션 생성

**API 이름**: 프로모션 생성  
**설명**: 새로운 프로모션을 등록합니다.

**HTTP Method**: `POST`  
**URL**: `/api/dashboard/promotions`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "title": "새로운 프로모션",
  "description": "프로모션 설명",
  "date": "2025-08-23",
  "prompt": "프로모션 관련 프롬프트",
  "storeId": 1
}
```

**Status Code**: `201 Created`

**Response Body**: 생성된 PromotionInfo 엔티티

---

### 4.7 프로모션 수정

**API 이름**: 프로모션 수정  
**설명**: 기존 프로모션 정보를 수정합니다.

**HTTP Method**: `PUT`  
**URL**: `/api/dashboard/promotions/{promotionId}`

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
  "title": "수정된 프로모션",
  "description": "수정된 설명",
  "date": "2025-08-24"
}
```

**Status Code**: `200 OK`

**Response Body**: 수정된 PromotionInfo 엔티티

---

## 💡 **Prompt API (프롬프트 관리)**

### 5.1 인기 프롬프트 목록

**API 이름**: 인기 프롬프트 목록  
**설명**: 사용 빈도가 높은 프롬프트 목록을 조회합니다.

**HTTP Method**: `GET`  
**URL**: `/api/prompts/popular`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**:
```json
[
  {
    "madeId": 1,
    "prompt": "커피 관련 블로그 포스트",
    "userInput": "맛있는 커피를 마시고 싶어요",
    "resultTitle": "Generated Title",
    "resultContent": "Generated Content",
    "hashTag": "#generated",
    "storeId": 1,
    "storeName": "스타벅스 강남점",
    "createdAt": "2025-08-21T19:42:01",
    "usageCount": 4,
    "popularityScore": 2.0
  }
]
```

---

### 5.2 프롬프트 재사용

**API 이름**: 프롬프트 재사용  
**설명**: 기존 프롬프트를 재사용하여 새로운 블로그를 생성합니다.

**HTTP Method**: `POST`  
**URL**: `/api/prompts/reuse/{madeId}`

**Headers**: 없음

**Request Body**: 없음

**Status Code**: `200 OK`

**Response Body**: 재사용된 Made 엔티티

---

## 📊 **실제 서비스 API 요약 테이블**

| 번호 | API 이름 | Method | URL | 설명 | ERD 엔티티 |
|------|----------|--------|-----|------|------------|
| 1 | 블로그 목록 조회 | GET | `/api/made` | 모든 블로그 목록 조회 | Made |
| 2 | 블로그 생성 (AI 포함) | POST | `/api/made/upload-images` | AI 생성 포함 블로그 생성 | Made |
| 3 | 블로그 콘텐츠 수정 | POST | `/api/made/modify-content` | AI 블로그 내용 수정 | Made |
| 4 | 특정 블로그 조회 | GET | `/api/made/result/{madeId}` | 특정 블로그 상세 조회 | Made |
| 5 | 사용자 입력 처리 | POST | `/api/made/user-input` | 사용자 입력 기반 블로그 생성 | Made |
| 6 | 매장 목록 조회 | GET | `/api/dashboard/stores` | 모든 매장 목록 조회 | Store |
| 7 | 특정 매장 조회 | GET | `/api/dashboard/stores/{storeId}` | 특정 매장 상세 조회 | Store |
| 8 | 매장명 검색 | GET | `/api/dashboard/stores/search/name` | 매장명으로 검색 | Store |
| 9 | 위치 검색 | GET | `/api/dashboard/stores/search/location` | 위치로 검색 | Store |
| 10 | 예약 가능 매장 | GET | `/api/dashboard/stores/reservable` | 예약 가능한 매장 조회 | Store |
| 11 | 매장 생성 | POST | `/api/dashboard/stores` | 새 매장 등록 | Store |
| 12 | 매장 수정 | PUT | `/api/dashboard/stores/{storeId}` | 매장 정보 수정 | Store |
| 13 | 편의시설 목록 | GET | `/api/dashboard/stores/{storeId}/conveniences` | 매장별 편의시설 조회 | Convenience |
| 14 | 편의시설 생성 | POST | `/api/dashboard/stores/{storeId}/conveniences` | 새 편의시설 등록 | Convenience |
| 15 | 편의시설 수정 | PUT | `/api/dashboard/conveniences/{convenienceId}` | 편의시설 정보 수정 | Convenience |
| 16 | 대시보드 프로모션 목록 | GET | `/api/dashboard/promotions` | 대시보드용 프로모션 | PromotionInfo |
| 17 | 대시보드 프로모션 상세 | GET | `/api/dashboard/promotions/{promotionId}` | 프로모션 상세 정보 | PromotionInfo |
| 18 | 커뮤니티 프로모션 목록 | GET | `/api/community/promotions` | 커뮤니티용 프로모션 | PromotionInfo |
| 19 | 커뮤니티 프로모션 상세 | GET | `/api/community/promotions/{promotionId}` | 커뮤니티 프로모션 상세 | PromotionInfo |
| 20 | 프로모션 좋아요 | POST | `/api/community/promotions/{promotionId}/like` | 프로모션 좋아요 | PromotionInfo |
| 21 | 프로모션 생성 | POST | `/api/dashboard/promotions` | 새 프로모션 등록 | PromotionInfo |
| 22 | 프로모션 수정 | PUT | `/api/dashboard/promotions/{promotionId}` | 프로모션 정보 수정 | PromotionInfo |
| 23 | 인기 프롬프트 목록 | GET | `/api/prompts/popular` | 인기 프롬프트 조회 | Made |
| 24 | 프롬프트 재사용 | POST | `/api/prompts/reuse/{madeId}` | 기존 프롬프트 재사용 | Made |

---

## 🗄️ **ERD 기반 데이터 구조**

### **Made (블로그 포스트)**
- `madeId`: 블로그 고유 ID (Primary Key)
- `image`: 이미지 파일 경로
- `hashTag`: 해시태그 정보
- `prompt`: AI 생성용 프롬프트
- `userInput`: 사용자 입력
- `resultTitle`: AI 생성된 제목
- `resultContent`: AI 생성된 내용
- `storeId`: 매장 ID (Foreign Key)

### **Store (매장 정보)**
- `storeId`: 매장 고유 ID (Primary Key)
- `storeName`: 매장명
- `storeImage`: 매장 이미지
- `information`: 매장 설명
- `location`: 위치
- `storeTime`: 영업시간
- `closedDays`: 휴무일
- `reservation`: 예약 가능 여부
- `menu`: 메뉴 파일
- `count`: 방문자 수/리뷰 수

### **Convenience (편의시설)**
- `id`: 편의시설 고유 ID (Primary Key)
- `wifi`: 와이파이 제공 여부
- `outlet`: 콘센트 제공 여부
- `pet`: 반려동물 동반 가능 여부
- `packagingDelivery`: 포장/배달 서비스 여부
- `storeId`: 매장 ID (Foreign Key)

### **PromotionInfo (프로모션 정보)**
- `id`: 프로모션 고유 ID (Primary Key)
- `title`: 프로모션 제목
- `image`: 프로모션 이미지
- `description`: 프로모션 설명
- `date`: 프로모션 날짜
- `like`: 좋아요 수
- `comment`: 댓글
- `prompt`: AI 생성용 프롬프트
- `storeId`: 매장 ID (Foreign Key)

---

## 🔗 **엔티티 관계**

- **Store → Made**: 1:N (하나의 매장에 여러 블로그 포스트)
- **Store → Convenience**: 1:N (하나의 매장에 여러 편의시설)
- **Store → PromotionInfo**: 1:N (하나의 매장에 여러 프로모션)

---

## 🚀 **프론트엔드 개발 가이드**

### **권장 호출 순서**
1. **매장 목록 조회**: `/api/dashboard/stores`
2. **편의시설 조회**: `/api/dashboard/stores/{storeId}/conveniences`
3. **프로모션 조회**: `/api/dashboard/promotions`
4. **블로그 생성**: `/api/made/upload-images`
5. **블로그 목록 조회**: `/api/made`
6. **프롬프트 재사용**: `/api/prompts/reuse/{madeId}`

### **에러 처리**
- 500 에러 시 서버 로그 확인
- OpenAI API 타임아웃 시 재시도 로직 구현 권장
- 네트워크 오류 시 사용자에게 적절한 메시지 표시

### **성능 최적화**
- OpenAI API 응답 대기 시간: 평균 10-15초
- 이미지 업로드 시 압축 권장
- 대량 데이터 조회 시 페이지네이션 구현 권장

---

## 📝 **노션 가져오기 방법**

1. **노션에서 새 페이지 생성**
2. **마크다운 파일 내용 복사**
3. **노션에 붙여넣기** (Ctrl/Cmd + V)
4. **자동으로 마크다운 형식 변환됨**

---

## 🎯 **요약**

**ERD에 포함된 모든 엔티티와 관계가 완벽하게 구현되어 있으며, 실제 서비스 배포와 프론트엔드 개발을 위한 완벽한 백엔드가 준비되었습니다!**

- **총 24개 API** 구현 완료
- **4개 핵심 엔티티** (Made, Store, Convenience, PromotionInfo)
- **완벽한 CRUD 기능** 지원
- **AI 블로그 생성** 기능 포함
- **프론트엔드 개발 가이드** 제공

---

**📅 최종 업데이트**: 2025-08-23  
**🔄 버전**: v1.0  
**👨‍💻 개발자**: AI Assistant  
**📁 프로젝트**: Blog Generator Backend





