# CLAUDE.md — soma2026-lab-android-architecture

## 프로젝트 개요

Android **Layered(앱) 아키텍처**와 **Clean 아키텍처**를 멀티모듈 환경에서 나란히 실습하는 연습용 저장소.  
두 아키텍처가 동일한 core 인프라를 공유하면서 각자의 방식으로 구현되는 것을 비교·실험하는 것이 목표다.

---

## 최종 모듈 구조

```
root/
├── app-layered/              # Layered 아키텍처 앱 진입점
├── app-clean/                # Clean 아키텍처 앱 진입점
│
├── layered/
│   ├── layered-presentation/ # UI(ViewModel, Compose Screen)
│   ├── layered-domain/       # UseCase, Repository 인터페이스
│   └── layered-data/         # Repository 구현체, DataSource
│
├── clean/
│   ├── clean-presentation/   # UI(ViewModel, Compose Screen)
│   ├── clean-domain/         # Entity, UseCase, Repository 인터페이스 (순수 Kotlin)
│   └── clean-data/           # Repository 구현체, DataSource, Mapper
│
├── core/
│   ├── core-ui/              # 공통 Compose 컴포넌트, Theme
│   ├── core-network/         # Retrofit, OkHttp, GSON 설정 (NetworkModule)
│   └── core-common/          # 확장함수, 유틸, 상수
│
└── build-logic/              # Convention Plugin (공통 빌드 설정)
    └── convention/
```

> **현재 상태**: `:app` 단일 모듈만 존재. 이슈 #4 작업으로 위 구조로 전환 예정.

---

## 기술 스택 & 버전

| 항목 | 버전 |
|------|------|
| AGP | 9.2.0 |
| Kotlin | 2.2.10 |
| Compose BOM | 2026.02.01 |
| minSdk | 28 |
| targetSdk / compileSdk | 36 |
| DI | Hilt (이슈 #5) |
| 네트워크 | Retrofit + OkHttp + GSON |
| 네비게이션 | Compose Navigation |

버전은 `gradle/libs.versions.toml` (Version Catalog)에서 중앙 관리한다.

---

## 빌드 규칙

### Convention Plugin (`build-logic/`)
- 반복되는 android 설정(compileSdk, jvmTarget, Compose 활성화 등)은 Convention Plugin으로 추출한다.
- 각 모듈의 `build.gradle.kts`는 `alias(libs.plugins.convention.*)` 형태로만 플러그인을 선언하고, 세부 설정은 플러그인 안에 둔다.

### 의존성 방향
```
app-layered / app-clean
    └─ layered-* / clean-*  (각 아키텍처 모듈)
            └─ core-*       (공유 인프라)
```
- 역방향 참조 금지: core가 layered·clean을 참조하지 않는다.
- layered와 clean은 서로를 참조하지 않는다.
- domain 모듈은 Android 프레임워크에 의존하지 않는다 (순수 Kotlin/Java).

---

## 아키텍처별 규칙

### Layered 아키텍처 (`layered-*`)
- Presentation → Domain → Data 단방향 흐름
- Domain 레이어에 비즈니스 로직 집중 (UseCase 클래스)
- ViewModel은 `@HiltViewModel`로 주입
- Repository 인터페이스: domain, 구현체: data

### Clean 아키텍처 (`clean-*`)
- `clean-domain`은 **순수 Kotlin 모듈** — Android 의존성 없음
- Entity는 domain에만 존재하며 data 레이어에서는 DTO + Mapper로 변환
- UseCase는 단일 책임: 파일 하나에 `operator fun invoke()` 하나
- `@Binds`를 사용한 인터페이스 바인딩 방식으로 DI

---

## 코딩 컨벤션

- 언어: **Kotlin** 전용, Java 파일 추가 금지
- UI: **Jetpack Compose** 전용, XML 레이아웃 금지
- 상태 관리: `StateFlow` + `UiState` sealed class/data class
- 비동기: Kotlin Coroutines + Flow
- 패키지 네이밍: `com.soma2026.lab.<module>.<layer>` (예: `com.soma2026.lab.layered.presentation`)
- 파일 하나에 클래스/인터페이스 하나 (테스트용 헬퍼 제외)

---

## 이슈 & 작업 순서

| 이슈 | 내용 | 브랜치 |
|------|------|--------|
| #4 | 멀티 모듈 구조 설계 및 리팩토링 | `feature/multi-module-setup` |
| #5 | Hilt DI 환경 구축 및 기초 설정 | `feature/hilt-setup` |

**작업 순서**: #4 완료 → #5 진행 (모듈이 없으면 DI 설정 불가)

---

## 자주 쓰는 명령어

```bash
# 전체 빌드
./gradlew assembleDebug

# 특정 모듈만 빌드
./gradlew :app-layered:assembleDebug

# lint 검사
./gradlew lint

# 테스트 실행
./gradlew test

# 의존성 트리 확인
./gradlew :app-layered:dependencies
```

---

## GitHub 참고

- 이슈: `gh issue list` / `gh issue view <번호>`
- PR: `gh pr create` / `gh pr list`
- 레이블: `🌱 init`, `🛠 refactor`, `✨ feat`, `🐛 fix`
