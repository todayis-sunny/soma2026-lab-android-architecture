# 트러블슈팅 기록 — AGP 9.x + Hilt

> 날짜: 2026-04-22  
> 환경: AGP 9.2.0 / Kotlin 2.2.10 / Hilt 2.59.2 / KSP 2.2.10-2.0.2

---

## 1. `kotlin.android` 플러그인 충돌

### 증상
```
Cannot add extension with name 'kotlin'
```

### 원인
AGP 9.x는 built-in Kotlin을 기본 제공.
`kotlin.android` 플러그인을 별도로 적용하면 `kotlin` extension이 이중 등록되어 충돌.

### 해결
`kotlin.android` 플러그인을 **제거**. AGP 9.x가 자동으로 Kotlin 컴파일을 처리함.

---

## 2. KSP 버전 `2.2.10-1.0.31` 미존재

### 증상
```
Could not resolve com.google.devtools.ksp:2.2.10-1.0.31
```

### 원인
KSP 2.2.x부터 버전 체계 변경: `<kotlin버전>-<ksp버전>` 형식에서 `<kotlin버전>-<major.minor.patch>` 형식으로 변경.

### 해결
`ksp = "2.2.10-2.0.2"` 사용 (Maven Central에서 실제 존재하는 버전 확인)

---

## 3. Hilt 2.51.1 "Android BaseExtension not found"

### 증상
```
Failed to apply plugin 'com.google.dagger.hilt.android'
Android BaseExtension not found
```

### 원인
Hilt 2.51.1이 AGP 9.x의 새로운 API를 지원하지 않음.

### 해결
Hilt **2.59.2**로 업그레이드 (AGP 9.x 공식 지원 버전)

---

## 4. Convention Plugin `CommonExtension` 타입 오류

### 증상
```
No type arguments expected for CommonExtension<*,*,*,*,*,*>
```

### 원인
Kotlin DSL에서 `CommonExtension`에 타입 파라미터를 명시하면 컴파일 오류 발생.

### 해결
`configureAndroid()` 함수의 파라미터 타입을 `CommonExtension<*,*,*,*,*,*>` → `BaseExtension`으로 변경.

---

## 5. `annotationProcessor` — Kotlin 파일 미처리 → `Hilt_*` 클래스 미생성

### 증상 (런타임 크래시)
```
FATAL EXCEPTION: main
java.lang.RuntimeException: Unable to instantiate application LayeredApp
ClassNotFoundException: Didn't find class "...LayeredApp"
Suppressed: NoClassDefFoundError: Failed resolution of: Hilt_LayeredApp
```

### 원인
`annotationProcessor`는 **Java 소스 파일만** 처리함.
`LayeredApp.kt`, `CleanApp.kt` 등 Kotlin 파일에 대해 `Hilt_*` 클래스를 생성하지 않음.
Hilt Gradle 플러그인은 바이트코드를 변환해 `LayeredApp`이 `Hilt_LayeredApp`을 상속하도록 만들지만,
`Hilt_LayeredApp` 자체가 존재하지 않아 런타임에 크래시.

### 시도한 해결책과 결과

| 시도 | 결과 |
|------|------|
| `kotlin.kapt` 플러그인 적용 | ❌ AGP 9.x built-in Kotlin과 충돌 |
| `ksp(...)` 단독 사용 (플러그인 없이) | ❌ `Unresolved reference 'ksp'` |
| KSP standalone 플러그인 적용 | ❌ `kotlin.sourceSets not allowed` |
| KSP + `android.disallowKotlinSourceSets=false` | ✅ 정상 동작 |

### 최종 해결

**Step 1**: 모든 Hilt 사용 모듈에 KSP 플러그인 적용
```kotlin
// build.gradle.kts
plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}
```

**Step 2**: `annotationProcessor` → `ksp`로 변경
```kotlin
dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)   // annotationProcessor 대신
}
```

**Step 3**: `gradle.properties`에 플래그 추가
```properties
# KSP standalone plugin이 kotlin.sourceSets DSL을 사용하도록 허용
android.disallowKotlinSourceSets=false
```

---

## 6. `kotlin.kapt` 플러그인 — built-in Kotlin 충돌

### 증상
```
The 'org.jetbrains.kotlin.kapt' plugin is not compatible with built-in Kotlin support.
Solution:
  - [Recommended] Migrate this project to built-in Kotlin.
  - Or set 'android.builtInKotlin=false' and 'android.newDsl=false'
```

### 원인
AGP 9.x built-in Kotlin은 `kotlin.android`뿐 아니라 `kotlin.kapt`도 명시적으로 차단.

### 교훈
AGP 9.x에서 어노테이션 처리 선택지:

| 방법 | 가능 여부 | 이유 |
|------|----------|------|
| `annotationProcessor` | 제한적 (Java only) | Kotlin 파일 미처리 |
| `kotlin.kapt` | ❌ | built-in Kotlin과 충돌 |
| KSP standalone plugin | ✅ (플래그 필요) | `android.disallowKotlinSourceSets=false` 필요 |

---

## 최종 AGP 9.x + Hilt 설정 요약

```toml
# libs.versions.toml
[versions]
agp = "9.2.0"
kotlin = "2.2.10"
hilt = "2.59.2"
ksp = "2.2.10-2.0.2"

[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
```

```properties
# gradle.properties
android.disallowKotlinSourceSets=false
```

```kotlin
// 각 Android 모듈 build.gradle.kts
plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}
```
