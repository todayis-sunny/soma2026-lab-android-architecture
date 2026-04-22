# 대화 요약 — 2026-04-22

## 작업 목표

Android **Layered 아키텍처**와 **Clean 아키텍처**를 멀티모듈 환경에서 나란히 실습하는 저장소 구축.
이슈 #4(멀티모듈 구조), #5(Hilt DI) 순서로 진행.

---

## 이슈 #4 — 멀티모듈 구조 설계

### 완성된 모듈 구조

```
root/
├── app-layered/              # Layered 아키텍처 앱 진입점
├── app-clean/                # Clean 아키텍처 앱 진입점
├── layered/
│   ├── layered-presentation/
│   ├── layered-domain/
│   └── layered-data/
├── clean/
│   ├── clean-presentation/
│   ├── clean-domain/         # 순수 Kotlin JVM 모듈
│   └── clean-data/
├── core/
│   ├── core-ui/
│   └── core-network/
└── build-logic/
    └── convention/           # Convention Plugin
```

### 주요 결정사항

| 항목 | 결정 |
|------|------|
| 기존 `:app` | `:app-layered`로 이름 변경 |
| `clean-domain` | 순수 Kotlin JVM 모듈 (Android 의존성 없음) |
| `build-logic/` | Convention Plugin으로 반복 빌드 설정 통합 |
| 의존성 방향 | `app-*` → `layered-*/clean-*` → `core-*` (역방향 금지) |
| `@Inject` in clean-domain | `javax.inject:javax.inject:1` (JSR-330) 추가 |

### Convention Plugin 목록

| Plugin ID | 역할 |
|-----------|------|
| `soma2026.android.application` | 앱 모듈 공통 설정 |
| `soma2026.android.library` | 라이브러리 모듈 공통 설정 |
| `soma2026.android.compose` | Compose 활성화 |
| `soma2026.kotlin.jvm` | 순수 Kotlin JVM 모듈 |
| `soma2026.android.hilt` | Hilt 플러그인 + 의존성 |

---

## 이슈 #5 — Hilt DI 환경 구축

### 최종 설정 스택

| 항목 | 버전/방식 |
|------|----------|
| Hilt | 2.59.2 (AGP 9.x 지원 최소 버전) |
| 어노테이션 처리 | KSP (standalone plugin) |
| AGP 호환 설정 | `android.disallowKotlinSourceSets=false` |

### 모듈별 Hilt 적용 내용

| 모듈 | 적용 내용 |
|------|----------|
| `app-layered` | `@HiltAndroidApp` (LayeredApp) |
| `app-clean` | `@HiltAndroidApp` (CleanApp) |
| `layered-presentation` | `@HiltViewModel` (SampleViewModel) |
| `layered-data` | `@Binds` RepositoryModule |
| `clean-presentation` | `@HiltViewModel` (SampleViewModel) |
| `clean-data` | `@Binds` RepositoryModule |
| `core-network` | `@Provides` NetworkModule (Retrofit, OkHttp, Gson) |

### 아키텍처별 DI 방식 차이

| 구분 | Layered | Clean |
|------|---------|-------|
| UseCase | 없음 (ViewModel이 Repository 직접 호출) | `GetSampleDataUseCase` |
| 바인딩 | `@Binds` | `@Binds` |
| Domain 의존성 | Android OK | Android 의존성 없음 |

---

## 커밋 히스토리

```
fix(#5): annotationProcessor → ksp로 교체하여 Hilt 코드 생성 수정  ← Closes #5
feat(#5): app-layered/app-clean @HiltAndroidApp + @AndroidEntryPoint 적용
feat(#5): clean-data RepositoryModule @Binds 작성
feat(#5): layered-presentation @HiltViewModel 적용
feat(#5): layered-data RepositoryModule 작성
chore(#5): Hilt 버전 및 AGP 9.x 어노테이션 처리 방식 확정
feat(#5): build-logic Hilt Convention Plugin 추가
chore(#5): Hilt + KSP 버전 및 플러그인 추가
feat(#4): build-logic Convention Plugin 구성                         ← Closes #4
feat(#4): :core-network 모듈 생성
feat(#4): :core-ui 모듈 생성
feat(#4): clean-presentation/domain/data 모듈 생성
feat(#4): layered-presentation/domain/data 모듈 생성
feat(#4): :app-clean 모듈 신규 생성
refactor(#4): :app 모듈을 :app-layered로 이름 변경
```

---

## PR

- **PR #6**: `feature/multi-module-setup` → `main`
  - 이슈 #4, #5 전체 작업 포함
