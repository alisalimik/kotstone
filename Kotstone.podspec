Pod::Spec.new do |s|
  s.name             = 'Kotstone'
  s.version          = '1.0.0-alpha01'
  s.summary          = 'High-level Multiplatform wrapper for the Capstone disassembly engine'
  s.description      = <<-DESC
Kotstone Kotlin Multiplatform Mobile library packaged as a framework for iOS, macOS, tvOS, and watchOS. High-level wrapper for the Capstone disassembly engine.
  DESC
  s.homepage         = 'https://github.com/alisalimik/kotstone'
  s.license          = { :type => 'Apache-2.0', :file => 'LICENSE' }
  s.author           = { 'Ali Salimi' => 'alisalimik2002@gmail.com' }
  s.source           = { :http => 'https://github.com/alisalimik/kotstone/releases/download/v1.0.0-alpha01/KotstoneKit.xcframework.zip' }

  # Platforms
  s.ios.deployment_target     = '14.0'
  s.tvos.deployment_target    = '14.0'
  s.watchos.deployment_target = '7.0'
  s.osx.deployment_target     = '12.0'
  s.vendored_frameworks = 'KotstoneKit.xcframework'
  s.static_framework = true

  # ARC
  s.requires_arc = true

  # Disable bitcode (XCFrameworks don't need it)
  s.pod_target_xcconfig = {
    'ENABLE_BITCODE' => 'NO',
    'GIT_REPO' => 'https://github.com/alisalimik/kotstone.git',
    'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386',
    'EXCLUDED_ARCHS[sdk=appletvsimulator*]' => 'i386',
    'EXCLUDED_ARCHS[sdk=watchsimulator*]' => 'i386'
  }

  s.user_target_xcconfig = {
    'ENABLE_BITCODE' => 'NO',
    'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386',
    'EXCLUDED_ARCHS[sdk=appletvsimulator*]' => 'i386',
    'EXCLUDED_ARCHS[sdk=watchsimulator*]' => 'i386'
  }

  # Skip validation for tvOS if simulator is not available
  s.swift_version = '5.0'
end