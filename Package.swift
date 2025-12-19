// swift-tools-version:5.8
import PackageDescription

let package = Package(
    name: "Kotstone",
    platforms: [
        .iOS(.v14),
        .macOS(.v12),
        .tvOS(.v14),
        .watchOS(.v7)
    ],
    products: [
        .library(
            name: "KotstoneKit",
            targets: ["KotstoneKit"]
        )
    ],
    targets: [
        .binaryTarget(
            name: "KotstoneKit",
            url: "https://github.com/alisalimik/kotstone/releases/download/v1.0.0-alpha01/KotstoneKit.xcframework.zip",
            checksum: "e0dc9301ee0c45f4dd95b5fad64843c24e2e455b31d997975e3dd14997ec9d89"
        )
    ]
)