# soma2026-lab-android-architecture
AI・SW Maestro 2026 Laboratory : 안드로이드 Layered Architecture와 Clean Architecture

## 질문
https://github.com/todayis-sunny/soma2026-lab-android-architecture/discussions

## 모듈 의존 그래프
<img width="50%" height="auto" alt="modules-graph" src="https://github.com/user-attachments/assets/3689904b-1024-40a8-903c-b222fdb7227a" />


## 두 아키텍처 차이 (작성 중)
| 항목	| Layered Architecture | Clean Architecture |
| --- | ---- | ---- |
| 모듈 의존성 방향 | Presentation → Domain → Data	| Presentation → Domain ← Data |
| domain 모듈 타입 | android.library (Data 의존으로 오염) |	kotlin.jvm (순수 Kotlin/Java) |
| Repository | UseCase가 구현체(Impl) 직접 참조 | UseCase가 인터페이스 참조, 구현체는 Data에 위치 |
| DTO 위치	| Domain UseCase가 DTO를 알고 직접 변환 | Domain은 DTO를 모름, Data의 Mapper가 변환
| 매핑 로직 위치	| layered-domain UseCase 내부 | clean-data Mapper
| DI 바인딩 |	구현체 직접 주입 | @Binds를 통해 인터페이스 ↔ 구현체 연결
| 도메인 테스트 대역 | MockK (구현체 모킹) |  Fake (인터페이스 직접 구현)
| DTO 변경 영향	| Domain 테스트까지 영향 |  Data Mapper 테스트만 영향
| 도메인 순수성 |Android 프레임워크와 결합됨 |  Android 의존성 완전 분리 (Pure Kotlin)
