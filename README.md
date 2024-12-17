# LoveMarker - Android

<img width="700" src="https://github.com/user-attachments/assets/7fa94ec6-01ff-443d-a5b2-299fd8ca1e8c"/>

## 💁🏻‍♀️ 서비스 소개

LoveMarker는 **연인 간의 추억을 지도 상에 간편하게 기록할 수 있는 아카이빙 앱 서비스**입니다.

일반적인 갤러리 앱은 연인 간의 추억을 따로 관리하기 어렵고, 구글 포토는 지도 상에 사진을 표시하는 기능 정도만 제공하고 있습니다.

LoveMarker는 초대 코드 기반으로 **커플을 매칭**하여, 연인 간의 **프라이빗한 아카이빙 플랫폼**을 구축할 수 있습니다.

그리고 사진, 장소, 날짜, 내용을 기반으로 **간편하게 추억을 기록하고 연인과 공유**할 수 있습니다.

## 📁 APK 다운로드

https://github.com/LoveMarker/LoveMarker-Android/releases/tag/v1.0.0

## ✨ 핵심 기능

### 온보딩 및 초대 코드 생성

https://github.com/user-attachments/assets/7dabec04-a0db-44f5-b7fe-9f3c13d2fe02

### 초대 코드 입력

https://github.com/user-attachments/assets/079c3038-0950-466d-a541-ebf2ac6869cb

### 추억 업로드 및 조회

https://github.com/user-attachments/assets/95fcd655-88d1-4fb2-95f8-5d3b8a8767f1

## 👏 앞으로 개선할 점

- [ ] image url로부터 장소, 날짜 데이터 추출
- [ ] 복수 이미지 업로드 시간 단축
- [ ] 지도 로딩 시간 단축
- [ ] 이미지 캐싱으로 로딩 시간 단축
- [ ] 앱 용량 축소 및 난독화

## 🧐 트러블슈팅

[https://velog.io/@jxlhe46/series/트러블슈팅](https://velog.io/@jxlhe46/series/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85)

## 🛠️ 사용 기술 스택

| 구분 | 기술 스택 |
| --- | --- |
| Architecture | MVI, Clean Architecture  |
| Build Configuration | Gradle Version Catalog, Multi-Module |
| JetPack | Compose, Credential Manager, DataStore, Paging3 |
| Dependency Injection | Hilt |
| Network | Retrofit, OkHttp, kotlinx.serialization |
| Asynchronous Processing | Coroutine, Flow |
| Third Party Library | Google Maps, Coil, Timber |
| Design System  | Material3  |

## ⚙️ 모듈 의존성 그래프

![project dot](https://github.com/user-attachments/assets/fe87069f-f29b-4c44-908d-dc14f1f30f4e)

## 🌱 팀원 구성

|[@이하은](https://github.com/leeeha)|[@전선희](https://github.com/funnysunny08)| 
|:---:| :---: | 
| <img width="100" src="https://github.com/user-attachments/assets/e32f41ca-f004-4b56-be17-12bc6f2d2565" /> | <img width="100" src="https://github.com/user-attachments/assets/8a6bdfbb-a261-457d-a97b-1723bf85586d" /> | 
| **Android** | **Server** | 
