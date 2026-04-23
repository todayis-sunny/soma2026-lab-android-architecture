# 대화 요약 — 2026-04-23

## 작업 목표

프리미어리그 순위표 화면을 football-data.org API를 통해 구현.
Layered / Clean 두 아키텍처 모두에 적용하고 공통 UI는 `core-ui`로 분리.

---

## GitHub 설정

### `.github/copilot-instructions.md` 생성
- 코파일럿 PR 리뷰 언어를 한국어로 지정
- 프로젝트 규칙(의존성 방향, 코딩 컨벤션, 리뷰 체크리스트) 포함

### 의존성 그래프 확인 이슈
- `./gradlew :app-layered:dependencies` 실행 시 configuration 미지정으로 KSP 내부 설정까지 출력됨
- `--configuration releaseRuntimeClasspath` 옵션 추가로 해결
- `CLAUDE.md` 명령어 업데이트

---

## 이슈 #8 — core-network API 키 & Retrofit 클라이언트 구성

### 브랜치: `feature/football-api-setup` → PR #11 (머지 완료)

### 작업 내용
- `local.properties`에 `FOOTBALL_BASE_URL`, `FOOTBALL_API_KEY` 추가
- `core-network/build.gradle.kts`: `local.properties` 읽어 `BuildConfig` 생성
  - `file.inputStream().use { }` 로 스트림 안전하게 닫기
  - 키 누락 시 `GradleException`으로 빌드 실패 처리
- `ApiKeyInterceptor`: 모든 요청에 `X-Auth-Token` 헤더 자동 주입 (`header()` 사용)
- `NetworkModule`: `BuildConfig` 기반 baseUrl 적용, DEBUG 빌드에서만 로깅 활성화

### 코파일럿 리뷰 반영 (4개)
1. `inputStream` → `use { }` 로 리소스 누수 방지 ✅
2. 키 누락 시 `GradleException` 빌드 실패 처리 ✅
3. `addHeader` → `header` 교체 (중복 헤더 방지) ✅
4. `HttpLoggingInterceptor` DEBUG 빌드에서만 활성화 (제안2 선택) ✅

---

## 이슈 목록 재설계

### 이슈 구조
```
#13 [Epic] 프리미어리그 순위표 화면 구현
  ├─ #12 core-ui 공통 Compose 컴포넌트 및 테마 구성
  ├─ #9  Layered 아키텍처 순위표 화면 구현
  └─ #10 Clean 아키텍처 순위표 화면 구현
```
- PR은 하위 이슈마다 개별 생성
- Epic #13은 하위 이슈 3개 완료 후 수동 클로즈

---

## 이슈 #12 — core-ui 공통 컴포넌트

### 브랜치: `feature/core-ui-components` → PR #14 (머지 완료)

### 작업 내용
- `libs.versions.toml`: Coil 2.7.0 추가
- `core-ui/build.gradle.kts`: Coil `implementation` 의존성 추가
- **Theme**: `Color.kt` / `Typography.kt` (→ `AppTypography`) / `Theme.kt` (`AppTheme`)
- **Components**:
  - `TeamLogo`: Coil `AsyncImage` 래퍼
  - `FormBadge`: W/D/L 도트 뱃지 (`remember(form)`으로 캐싱)
  - `StandingRow`: 순위 한 줄 공통 컴포넌트

### 코파일럿 리뷰 반영 (4개 중 3개)
1. `Typography` 자기 참조 컴파일 에러 → `AppTypography`로 이름 변경 ✅
2. `Modifier.weight` import 누락 → 오탐 (RowScope 암시적 리시버로 정상 동작) ⏭
3. `form.split(",")` → `remember(form)`으로 캐싱 ✅
4. `api(coil)` → `implementation(coil)` 의존성 누수 제거 ✅

---

## 이슈 #9 — Layered 아키텍처 순위표 화면

### 브랜치: `feature/layered-standings` → PR #15

### 작업 내용

#### layered-domain
- `Standing` 도메인 모델
- `StandingsRepository` 인터페이스
- `GetStandingsUseCase` (`operator fun invoke()`)
- `javax.inject` 의존성 추가 (UseCase `@Inject` 사용)

#### layered-data
- `StandingsResponseDto` / `TeamDto` (API 응답 DTO)
- `PremierLeagueApiService` (Retrofit interface)
- `StandingsRepositoryImpl` (DTO → 도메인 변환 확장함수)
- `NetworkServiceModule` (ApiService Hilt 제공)
- `RepositoryModule`에 `StandingsRepository` 바인딩 추가

#### layered-presentation
- `StandingsUiState` (Loading / Success / Error)
- `StandingsViewModel` (`@HiltViewModel`, init에서 자동 로딩)
- `StandingsScreen` (core-ui 컴포넌트 활용)

#### app-layered
- `MainActivity`에 `StandingsScreen` 연결, `AppTheme` 적용
- `INTERNET` 퍼미션 추가
- 로컬 `ui/theme/` 디렉토리 제거
- `core-ui` 의존성 추가

### 트러블슈팅
- API 키 누락으로 400 에러 → `local.properties` 키 뒤 2자리 누락이었음, 재빌드로 해결
- 헤더-리스트 컬럼 정렬 불일치 → `StandingsHeader`에 `Arrangement.spacedBy(8.dp)` 적용

### 코파일럿 리뷰 반영 (2개)
1. 헤더 컬럼 폭을 `StandingRow`와 동일하게 맞춤 ✅
2. `first { }` → `find { } ?: throw IllegalStateException` 안전 처리 ✅

---

## 이슈 #10 — Clean 아키텍처 순위표 화면

### 브랜치: `feature/clean-standings` → PR #16

### Layered와의 차이점

| | Layered | Clean |
|---|---|---|
| domain 모듈 | Android library | 순수 Kotlin (`kotlin.jvm`) |
| domain 소스셋 | `src/main/java` | `src/main/kotlin` |
| DTO→모델 변환 | 확장함수 (inline) | `StandingMapper` 별도 클래스 |

### 작업 내용

#### clean-domain (`src/main/kotlin` — 순수 Kotlin)
- `Standing` Entity
- `StandingsRepository` 인터페이스
- `GetStandingsUseCase` (`operator fun invoke()`)

#### clean-data (`src/main/java` — Android library)
- `StandingsResponseDto` / `TeamDto`
- `PremierLeagueApiService`
- `StandingMapper` object (DTO → Entity 변환 전담)
- `StandingsRepositoryImpl`
- `NetworkServiceModule`, `RepositoryModule` (`@Binds`)

#### clean-presentation (`src/main/java` — Android library)
- `StandingsUiState` / `StandingsViewModel` / `StandingsScreen`
- core-ui 컴포넌트 활용, 헤더 레이아웃 layered와 동일하게 맞춤

#### app-clean
- `StandingsScreen` 연결, `AppTheme` 적용
- `INTERNET` 퍼미션 추가, 로컬 테마 제거

### 코파일럿 리뷰 반영
- PR 설명에서 소스셋 경로 혼동 → 모듈별 명확히 구분해서 수정
- 코멘트에 답글 추가

---

## PR 현황

| PR | 이슈 | 브랜치 | 상태 |
|---|---|---|---|
| #11 | #8 core-network | `feature/football-api-setup` | 머지 완료 |
| #14 | #12 core-ui | `feature/core-ui-components` | 머지 완료 |
| #15 | #9 Layered 순위표 | `feature/layered-standings` | 오픈 |
| #16 | #10 Clean 순위표 | `feature/clean-standings` | 오픈 |

---

## 주요 학습 포인트

- Convention Plugin에서 `VersionCatalogsExtension`으로 toml 참조 가능
- `android.disallowKotlinSourceSets=false`는 KSP + AGP 9.x 호환을 위해 필요 (에러 아님)
- `clean-domain`만 순수 Kotlin 모듈 (`kotlin.jvm`), data/presentation은 Android library
- `Arrangement.spacedBy()`를 쓰는 Row의 헤더는 동일한 spacing 규칙을 공유해야 정렬이 맞음
- BuildConfig는 빌드 시 주입되므로 `local.properties` 변경 후 반드시 재빌드 필요
