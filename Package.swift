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
            url: "<link to the uploaded XCFramework ZIP file>",
            checksum: "<checksum calculated for the ZIP file>"
        )
    ]
)