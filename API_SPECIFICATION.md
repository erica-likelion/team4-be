# 🚀 Blog Generator Backend API 명세서

## 📋 개요
- **Base URL**: `http://localhost:8081` (개발환경)
- **Content-Type**: `application/json`
- **인증**: 현재 토큰 인증 없음 (개발 단계)

---

## 🔍 **1. OpenAI API 테스트**

### 1.1 헬스 체크
```http
GET /api/test/openai/health
```

**응답 예시:**
```json
{
  "service": "OpenAI API Test Controller",
  "message": "OpenAI API 테스트 컨트롤러가 정상적으로 동작 중입니다.",
  "status": "OK",
  "timestamp": 1755876002025
}
```

### 1.2 제목 생성
```http
POST /api/test/openai/generate-title
Content-Type: application/json

{
  "userInput": "강남 카페 추천해주세요"
}
```

**응답 예시:**
```json
{
  "success": true,
  "generatedTitle": "```json\n{\n  \"title\": \"강남 최고의 힙한 카페! 놓치면 후회할 완벽한 공간 #인생샷\"\n}\n```",
  "userInput": "강남 카페 추천해주세요",
  "timestamp": 1755876011661
}
```

### 1.3 블로그 콘텐츠 생성
```http
POST /api/test/openai/generate-content
Content-Type: application/json

{
  "userInput": "강남 카페 추천해주세요",
  "storeInfo": "강남역 근처의 아늑한 카페"
}
```

**응답 예시:**
```json
{
  "generatedContent": "```json\n{\n  \"title\": \"강남역 근처, 마음을 녹이는 아늑한 카페 탐방기\",\n  \"content\": \"강남역 근처에 위치한 이 아늑한 카페는...\",\n  \"hashtags\": [\"#강남카페\", \"#아늑한분위기\", \"#커피맛집\", \"#데이트장소\", \"#강남역\"]\n}\n```"
}
```

---

## 📝 **2. Made API (블로그 생성)**

### 2.1 블로그 목록 조회
```http
GET /api/made
```

**응답 예시:**
```json
[
  {
    "madeId": 1,
    "image": null,
    "hashTag": "#generated",
    "prompt": "커피 관련 블로그 포스트",
    "userInput": "맛있는 커피를 마시고 싶어요",
    "resultTitle": "Generated Title",
    "resultContent": "Generated Content",
    "storeId": 1,
    "storeName": "스타벅스 강남점",
    "createdAt": "2025-08-21T19:42:01"
  }
]
```

### 2.2 블로그 생성 (이미지 업로드 포함)
```http
POST /api/made/upload-images
Content-Type: multipart/form-data

{
  "userInput": "아메리카노가 맛있는 카페",
  "storeId": 1,
  "hashTag": "#커피맛집 #강남카페",
  "detailedRequest": "특별히 아메리카노 맛을 강조해주세요",
  "images": [파일1, 파일2, ...] // 선택사항
}
```

**응답 예시:**
```json
{
  "success": true,
  "message": "블로그가 성공적으로 생성되었습니다.",
  "madeId": 13,
  "generatedTitle": "강남 최고의 아메리카노 맛집!",
  "generatedContent": "생성된 블로그 내용...",
  "generatedHashtags": ["#커피맛집", "#강남카페", "#아메리카노"]
}
```

### 2.3 블로그 콘텐츠 수정
```http
POST /api/made/modify-content
Content-Type: application/json

{
  "madeId": 13,
  "modificationRequest": "더 자세하고 감정적인 내용으로 수정해주세요"
}
```

**응답 예시:**
```json
{
  "success": true,
  "message": "블로그 내용이 성공적으로 수정되었습니다.",
  "modifiedContent": "수정된 블로그 내용..."
}
```

---

## 🏪 **3. Store API (매장 관리)**

### 3.1 매장 목록 조회
```http
GET /api/stores
```

**응답 예시:**
```json
[
  {
    "storeId": 1,
    "name": "스타벅스 강남점",
    "address": "서울시 강남구 테헤란로",
    "phone": "02-1234-5678",
    "description": "강남역 근처의 프리미엄 커피숍"
  }
]
```

### 3.2 매장 생성
```http
POST /api/stores
Content-Type: application/json

{
  "name": "새로운 카페",
  "address": "서울시 강남구",
  "phone": "02-1234-5678",
  "description": "매장 설명"
}
```

### 3.3 매장 수정
```http
PUT /api/stores/{storeId}
Content-Type: application/json

{
  "name": "수정된 카페명",
  "address": "수정된 주소",
  "phone": "02-9876-5432",
  "description": "수정된 설명"
}
```

### 3.4 매장 삭제
```http
DELETE /api/stores/{storeId}
```

---

## 🎯 **4. Promotion API (프로모션)**

### 4.1 대시보드 프로모션 목록
```http
GET /api/dashboard/promotions
```

**응답 예시:**
```json
[]
```

### 4.2 대시보드 프로모션 상세
```http
GET /api/dashboard/promotions/{promotionId}
```

### 4.3 커뮤니티 프로모션 목록
```http
GET /api/community/promotions
```

### 4.4 커뮤니티 프로모션 상세
```http
GET /api/community/promotions/{promotionId}
```

### 4.5 프로모션 좋아요
```http
POST /api/community/promotions/{promotionId}/like
```

---

## 🔧 **5. Prompt API (프롬프트 관리)**

### 5.1 인기 프롬프트 목록
```http
GET /api/prompts/popular
```

### 5.2 프롬프트 재사용
```http
POST /api/prompts/reuse/{madeId}
```

---

## 🧪 **6. Test API (테스트)**

### 6.1 기본 테스트
```http
GET /test
```

**응답 예시:**
```
Blog Generator Backend is running successfully!
```

### 6.2 OpenAI API 키 상태 확인
```http
GET /test/openai-key
```

**응답 예시:**
```json
{
  "message": "OpenAI API 키가 설정되지 않았습니다.",
  "hasApiKey": false,
  "timestamp": 1755845733095
}
```

---

## ⚠️ **7. 현재 알려진 문제점**

### 7.1 Store API
- **상태**: ❌ 500 Internal Server Error
- **문제**: 데이터베이스 스키마 또는 JPA 설정 문제
- **영향**: 매장 생성/수정/삭제 불가

### 7.2 Made API
- **상태**: ⚠️ 부분정상
- **문제**: 일부 데이터에서 "제목을 찾을 수 없습니다" / "내용을 찾을 수 없습니다"
- **영향**: 기존 데이터 일부 표시 문제

---

## 🚀 **8. 배포 전 체크리스트**

- [x] OpenAI API 연동 정상
- [x] 기본 애플리케이션 동작
- [x] Made API 기본 동작
- [ ] Store API 오류 해결 필요
- [ ] 데이터베이스 스키마 검증 필요
- [ ] 에러 핸들링 개선 필요

---

## 📞 **9. 연락처**

- **Backend Developer**: AI Assistant
- **Repository**: blog-generator-backend-backup
- **Last Updated**: 2025-08-23

---

## 🔄 **10. API 버전 관리**

- **현재 버전**: v1.0
- **호환성**: Spring Boot 3.5.4
- **데이터베이스**: MySQL 8.0
- **OpenAI 모델**: GPT-4o





