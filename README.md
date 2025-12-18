# Kapstone

[![Build](https://github.com/alisalimik/kotstone/actions/workflows/build.yml/badge.svg)](https://github.com/alisalimik/kotstone/actions/workflows/build.yml)
[![Test](https://github.com/alisalimik/kotstone/actions/workflows/test.yml/badge.svg)](https://github.com/alisalimik/kotstone/actions/workflows/test.yml)

**Kotstone** is a lightweight, efficient Kotlin Multiplatform binding for the [Capstone](https://www.capstone-engine.org/) disassembly engine. It provides a type-safe, idiomatic API for disassembling binary code across JVM, Android, iOS, macOS, Linux, Windows, and Web (JS/Wasm).

## Installation

### Gradle (Kotlin/Groovy)
```kotlin
implementation("ca.moheektech:kapstone:1.0.0-alpha01")
```
> **Note**: Resolves platform-specific native libraries automatically via Gradle variant matching.

### Maven
```xml
<dependency>
    <groupId>ca.moheektech</groupId>
    <artifactId>kapstone-jvm</artifactId>
    <version>1.0.0-alpha01</version>
</dependency>
```

### CocoaPods
```ruby
pod 'Kotstone'
```

### Swift Package Manager
```swift
dependencies: [
    .package(url: "https://github.com/alisalimik/kotstone.git", from: "1.0.0-alpha01")
]
```

## Requirements
- **Kotlin**: 2.3.0+
- **Java (JVM)**: JDK 17+
- **Targets**: Android 5.0+, Linux (x64/Arm64), Windows (x64), macOS 12+, iOS 14+, tvOS 14+, watchOS 7+.

## API Usage

### Kotlin Multiplatform
```kotlin
import ir.alisalimik.kotstone.api.CapstoneEngine
import ir.alisalimik.kotstone.enums.Architecture
import ir.alisalimik.kotstone.enums.Mode

// Automatic resource management
CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { engine ->
    val code = byteArrayOf(0x09, 0x00, 0x38.toByte(), 0xd5.toByte()) // "ret"
    
    engine.disassemble(code, address = 0x1000).onSuccess { instructions ->
        instructions.forEach { println("0x${it.address.toString(16)}: ${it.mnemonic} ${it.opStr}") }
    }.onFailure {
        println("Disassembly failed: ${it.message}")
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
- **macOS Machine** (for Apple targets/cross-compilation)
- **Toolchains**:
    - **Zig** (for Linux cross-compilation from macOS)
    - **Mingw-w64** (for Windows cross-compilation)

### Build Commands
```bash
# Build all targets native lib
./gradlew buildCapstoneAll

#build kotlin library
./gradlew build
```

## Contributing
Contributions are welcome! I specifically need help verifying and testing on more architectures (Linux ARM32/MIPS/etc.) and improving the Windows build chain.

## License
Distributed under the **APACHE-2.0** license.  
Bundles **Capstone Engine** which is available under the **BSD 3-Clause** license.
