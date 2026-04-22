# GitHub Copilot Instructions

## 언어
- PR 리뷰, 코드 리뷰, 코멘트, 제안 등 **모든 응답은 한국어**로 작성한다.

## 프로젝트 개요
- Android 멀티모듈 환경에서 **Layered 아키텍처**와 **Clean 아키텍처**를 나란히 실습하는 저장소다.
- 두 아키텍처가 동일한 `core` 인프라를 공유하면서 각자의 방식으로 구현된다.

## 모듈 구조 & 의존성 방향
```
app-layered / app-clean
    └─ layered-* / clean-*
            └─ core-*
```
- `core`가 `layered`·`clean`을 참조하는 역방향 의존성은 허용하지 않는다.
- `layered`와 `clean`은 서로를 참조하지 않는다.
- `clean-domain`은 순수 Kotlin 모듈 — Android 프레임워크 의존성 없음.

## 코딩 컨벤션
- **Kotlin 전용** — Java 파일 추가 금지.
- **Jetpack Compose 전용** — XML 레이아웃 금지.
- 상태 관리: `StateFlow` + `UiState` sealed class/data class.
- 비동기: Kotlin Coroutines + Flow.
- 파일 하나에 클래스/인터페이스 하나.
- 패키지 네이밍: `com.soma2026.lab.<module>.<layer>`.

## 아키텍처 규칙
- **Layered**: Presentation → Domain → Data 단방향 흐름. ViewModel은 `@HiltViewModel`.
- **Clean**: UseCase는 단일 책임(`operator fun invoke()` 하나). DI는 `@Binds` 방식.
- Convention Plugin(`build-logic/`)으로 공통 빌드 설정을 관리한다.

## PR 리뷰 시 중점 확인 항목
1. 의존성 방향이 위 규칙을 위반하지 않는지 확인한다.
2. `clean-domain`에 Android 의존성이 유입되지 않았는지 확인한다.
3. 코딩 컨벤션(Kotlin, Compose, 패키지명)을 준수했는지 확인한다.
4. UseCase가 단일 책임 원칙을 지키는지 확인한다.
5. 상태 관리에 `StateFlow` + `UiState` 패턴을 사용했는지 확인한다.
