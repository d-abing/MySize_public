## 마이 사이즈📏

![그래픽 이미지](https://github.com/user-attachments/assets/50068db9-002a-4b4f-8862-a83888d75440)

### 프로젝트 소개
잘 맞았던 옷 사이즈를 저장하고 관리하며, 사이즈와 함께 자신의 스타일을 공유할 수 있는 서비스입니다.


### 주요 기능
- **사이즈 저장 및 관리**
    - 카테고리 별로 사이즈를 저장하고 관리합니다.
    - 캡쳐된 사이즈 표 이미지를 불러와 자동으로 사이즈를 입력할 수 있게 하였습니다. (Google ML Kit OCR로 구현)
- **스타일 공유**
    - 옷 사이즈와 함께 본인의 옷 이미지를 업로드 할 수 있습니다.
    - 내 옷의 대표색 보기 탭에서는 자체적으로 구현한 알고리즘으로 퍼스널 컬러 분석 결과를 제공하고 있습니다.
    - 다른 사용자가 공유한 옷의 사이즈를 저장할 수 있습니다.
- **유저 검색, 팔로우, 차단, 신고**
    - 사용자의 사용 경험 향상을 위해 유저 검색, 팔로우, 차단, 신고 기능을 제공 하고 있습니다.
- **맞춤형 쇼핑몰, 사이즈 추천**
    - 사용자의 신체 사이즈 정보와 선호 스타일을 바탕으로 쇼핑몰을 추천합니다.
    - 기본 신체 사이즈 정보인 키·몸무게·성별을 기반으로 의류 실측값을 자동 추정하고, 입력된 신체 사이즈 정보가 있을 경우 보정하여 적절한 옷 사이즈를 추천합니다.
- **온라인 / 오프라인 모두 동작**
    - 온라인 상태에서 캐싱된 데이터로 오프라인에서도 일부 제한된 기능으로 정상 동작합니다.


### 기술
`Kotlin` `Jetpack compose` `Room` `Firebase Firestore` `Firebase Authentication` `DataStore` `Hilt(DI)` `Coil` `Lottie` `Google Sign-In` `Clean Architecture` `MVVM` `Coroutine` `Flow` `Google ML Kit OCR(Text Recognition API)` `Admob`


### 실행화면
<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6c419a52-dc06-4dee-a4c8-bf1b7b0dbc02" width="400"/></td>
    <td><img src="https://github.com/user-attachments/assets/db6f2a41-517a-40d4-a56d-b5a41d72e009" width="400"/></td>
    <td><img src="https://github.com/user-attachments/assets/aef5228c-dd4d-4f92-b1b0-a47203e61946" width="400"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/38de22be-7e2d-437c-baf3-a16ff8ae2cf4" width="400"/></td>
    <td><img src="https://github.com/user-attachments/assets/1b5a6bc7-eed1-4956-9371-949859780eef" width="400"/></td>
    <td><img src="https://github.com/user-attachments/assets/a26dc48b-1f1e-4f7d-bd19-fe08d41d612d" width="400"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/92a4b6db-931f-4cc8-b6c7-b1465895efab" width="400"/></td>
    <td><img src="https://github.com/user-attachments/assets/369a68e4-8c2c-4d8e-b85c-ebae4b872901" width="400"/></td>
    <td><img src="https://github.com/user-attachments/assets/e2c4a30d-6067-4eee-bf5d-8a7074369878" width="400"/></td>
  </tr>
</table>

