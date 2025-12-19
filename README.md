# Kotstone

[![Build](https://github.com/alisalimik/kotstone/actions/workflows/build.yml/badge.svg)](https://github.com/alisalimik/kotstone/actions/workflows/build.yml)
[![Test](https://github.com/alisalimik/kotstone/actions/workflows/test.yml/badge.svg)](https://github.com/alisalimik/kotstone/actions/workflows/test.yml)

**Kotstone** is a lightweight, efficient Kotlin Multiplatform binding for the [Capstone](https://www.capstone-engine.org/) disassembly engine. It provides a type-safe, idiomatic API for disassembling binary code across JVM, Android, iOS, macOS, Linux, Windows, and Web (JS/Wasm).

## Installation

### Gradle (Kotlin/Groovy)

#### Kotlin DSL
```kotlin
implementation("ir.alisalimik.kotstone:library:1.0.0-alpha01")
```

#### Groovy DSL
```groovy
implementation 'ir.alisalimik.kotstone:library:1.0.0-alpha01'
```

### Maven
```xml
<dependency>
    <groupId>ir.alisalimik.kotstone</groupId>
    <artifactId>library-jvm</artifactId>
    <version>1.0.0-alpha01</version>
</dependency>
```

### NPM/Yarn (JavaScript/TypeScript)
For web projects using JavaScript or TypeScript, install via npm:

```bash
npm install @alisalimik/kotstone
# or
yarn add @alisalimik/kotstone
```

**Package:** [@alisalimik/kotstone on npm](https://www.npmjs.com/package/@alisalimik/kotstone)

### CocoaPods

#### Podfile (Ruby)
```ruby
# Using pod name
pod 'Kotstone'

# Or using git URL
pod 'Kotstone', :git => 'https://github.com/alisalimik/kotstone.git', :tag => '1.0.0-alpha01'
```

#### Podfile.json
```json
{
  "dependencies": {
    "Kotstone": {
      "git": "https://github.com/alisalimik/kotstone.git",
      "tag": "1.0.0-alpha01"
    }
  }
}
```

### Swift Package Manager

#### Package.swift
Add the dependency to your `Package.swift` file:

```swift
// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "YourProject",
    platforms: [
        .iOS(.v14),
        .macOS(.v12),
        .tvOS(.v14),
        .watchOS(.v7)
    ],
    dependencies: [
        .package(url: "https://github.com/alisalimik/kotstone.git", from: "1.0.0-alpha01")
    ],
    targets: [
        .target(
            name: "YourTarget",
            dependencies: [
                .product(name: "Kotstone", package: "kotstone")
            ]
        )
    ]
)
```

#### Xcode
In Xcode, go to **File > Add Package Dependencies...** and enter:
```
https://github.com/alisalimik/kotstone.git
```

#### Swift Package Manager CLI
```bash
# Add package dependency
swift package add-dependency https://github.com/alisalimik/kotstone.git --from 1.0.0-alpha01

# Or manually edit Package.swift and resolve
swift package resolve
```

## Requirements
- **Kotlin**: 2.3.0+
- **Java (JVM)**: JDK 17+
- **Targets**: Android 5.0+, Linux (x64/Arm64), Windows (x64), macOS 12+, iOS 14+, tvOS 14+, watchOS 7+.

## API Usage

### Kotlin Multiplatform

> **Important for Web/JS/WASM targets:** You must call `initializeCapstone()` before using the API, as it loads the WASM binary at runtime. This is a suspendable function.
>
> **Note:** For JVM, Native, and Android-only projects, initialization is not required (but calling it is harmless).

```kotlin
import ir.alisalimik.kotstone.api.CapstoneEngine
import ir.alisalimik.kotstone.api.initializeCapstone
import ir.alisalimik.kotstone.enums.Architecture
import ir.alisalimik.kotstone.enums.Mode

suspend fun disassembleCode() {
    // Initialize Capstone (required for JS/WASM, no-op for JVM/Native/Android)
    initializeCapstone()

    // Automatic resource management
    CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { engine ->
        val code = byteArrayOf(0x09, 0x00, 0x38.toByte(), 0xd5.toByte()) // "ret"

        engine.disassemble(code, address = 0x1000).onSuccess { instructions ->
            instructions.forEach { println("0x${it.address.toString(16)}: ${it.mnemonic} ${it.opStr}") }
        }.onFailure {
            println("Disassembly failed: ${it.message}")
        }
    }
}
```

### Java
```java
import ir.alisalimik.kotstone.api.CapstoneEngine;
import ir.alisalimik.kotstone.enums.Architecture;
import ir.alisalimik.kotstone.enums.Mode;

try (CapstoneEngine engine = CapstoneEngine.build(Architecture.X86, Mode.MODE_64)) {
    byte[] code = {0x55, 0x48, (byte)0x8b, 0x05, (byte)0xb8, 0x13, 0x00, 0x00};

    var result = engine.disassemble(code, 0x1000, 0);
    if (result.isSuccess()) {
        for (Instruction insn : result.getOrThrow()) {
            System.out.printf("0x%x: %s %s%n", insn.getAddress(), insn.getMnemonic(), insn.getOpStr());
        }
    }
}
```

### TypeScript/JavaScript

For web projects, you must initialize the library before use to load the WASM module:

```typescript
import { CapstoneEngine, Architecture, Mode, initializeCapstone } from '@alisalimik/kotstone';

async function disassembleCode() {
    // Initialize Capstone WASM module (required for web targets)
    await initializeCapstone();

    // Create engine with ARM64 architecture
    const engine = CapstoneEngine.Companion.build(
        Architecture.ARM64,
        Mode.LITTLE_ENDIAN,
        (builder) => {
            builder.detail = true;
        }
    );

    try {
        // Disassemble ARM64 "ret" instruction
        const code = new Int8Array([0x09, 0x00, 0x38, 0xd5]);
        const result = engine.disassemble(code, 0x1000n);

        if (result.isSuccess()) {
            const instructions = result.getOrThrow();
            for (const insn of instructions) {
                console.log(`0x${insn.address.toString(16)}: ${insn.mnemonic} ${insn.opStr}`);
            }
        }
    } finally {
        engine.close();
    }
}

disassembleCode();
```

### Objective-C / Swift

For iOS, macOS, tvOS, and watchOS projects using Objective-C:

```objectivec
#import <KapstoneKit/KapstoneKit.h>

void disassembleCode() {
    // No initialization needed for native targets

    // Create engine with ARM64 architecture
    KapstoneKitCapstoneEngine *engine = [KapstoneKitCapstoneEngineCompanion.shared
        buildArchitecture:KapstoneKitArchitecture.arm64
        mode:KapstoneKitMode.littleEndian
        configure_:^(KapstoneKitCapstoneBuilder *builder) {
            builder.detail = YES;
        }];

    // Disassemble ARM64 "ret" instruction
    uint8_t bytes[] = {0x09, 0x00, 0x38, 0xd5};
    KapstoneKitKotlinByteArray *code = [KapstoneKitKotlinByteArray arrayWithSize:4];
    for (int i = 0; i < 4; i++) {
        [code setIndex:i value:bytes[i]];
    }

    id result = [engine disassembleCode:code address:0x1000 count:0];
    // Handle result...

    [engine close];
}
```

Or in Swift:

```swift
import KapstoneKit

func disassembleCode() {
    // No initialization needed for native targets

    // Create engine with ARM64 architecture
    let engine = CapstoneEngine.companion.build(
        architecture: .arm64,
        mode: .littleEndian
    ) { builder in
        builder.detail = true
    }

    defer { engine.close() }

    // Disassemble ARM64 "ret" instruction
    let code: [Int8] = [0x09, 0x00, 0x38, -43] // 0xd5 as signed byte
    let byteArray = KotlinByteArray(size: Int32(code.count))
    for (index, byte) in code.enumerated() {
        byteArray.set(index: Int32(index), value: byte)
    }

    let result = engine.disassemble(code: byteArray, address: 0x1000, count: 0)

    if result.isSuccess() {
        if let instructions = result.getOrNull() {
            for insn in instructions {
                if let instruction = insn as? Instruction {
                    print(String(format: "0x%llx: %@ %@",
                                 instruction.address,
                                 instruction.mnemonic,
                                 instruction.opStr))
                }
            }
        }
    }
}
```

> **Compiler Error?** If you encounter "Experimental API usage" errors, add this free compiler arg:  
```
compilerOptions {
    freeCompilerArgs.add("-Xcontext-parameters")
    freeCompilerArgs.add("-Xexpect-actual-classes")
    freeCompilerArgs.add("-Xreturn-value-checker=check")
}
```

## Build from Source

### Prerequisites
- **JDK 17+**
- **Android SDK platform 36 & NDK r29**
- **macOS Machine** (for Apple targets and cross-compilation to other platforms)
- **Emscripten SDK 4.0.21+** (required for WASM and JS targets)
- **Cross-compilation toolchains**:
    - **Linux targets from macOS**: Use [messense/homebrew-macos-cross-toolchains](https://github.com/messense/homebrew-macos-cross-toolchains)
    - **Windows target**: Mingw-w64
    - **Fallback**: Zig (especially on Windows hosts compiling for linux)

### Installing Prerequisites

#### Emscripten SDK (for Web/WASM targets)
```bash
# Clone and install Emscripten SDK
git clone https://github.com/emscripten-core/emsdk.git
cd emsdk
./emsdk install 4.0.21
./emsdk activate 4.0.21
source ./emsdk_env.sh
```

#### Cross-compilation toolchains (macOS)
```bash
# Add the cross-toolchains tap
brew tap messense/macos-cross-toolchains

# Install Linux cross-compilation toolchains
brew install x86_64-unknown-linux-gnu  # For Linux x86_64
brew install aarch64-unknown-linux-gnu # For Linux ARM64

# Install Windows cross-compilation toolchain
brew install mingw-w64

# Optional: Install Zig as fallback for other scenarios
brew install zig
```

### Build Commands
```bash
# Build all targets native lib dependency for all targets
./gradlew buildCapstoneAll

# Build android/jvm library
./gradlew assembleAndroidMain
./gradlew jvmJar

# build apple frameworks
./gradlew assembleKotstoneKitReleaseXCFramework

# Build web target groups
./gradlew buildCapstoneWasmJs  # WASM js target
./gradlew jsNodeProductionLibraryDistribution  # Typescript binding
./gradlew wasmJsNodeProductionLibraryDistribution

./gradlew buildCapstoneWasmWasi  # WASM WASI target
./gradlew wasmWasiNodeProductionLibraryDistribution

```

## Contributing
Contributions are welcome! I specifically need help verifying and testing on more architectures RISC-V/MIPS/etc.

## License
Distributed under the **APACHE-2.0** license.  
Bundles **Capstone Engine** which is available under the **BSD 3-Clause** license.
