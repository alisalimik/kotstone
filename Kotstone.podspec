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
  s.source           = { :http => '<URL-to-Kapstone.xcframework.zip>' }

  # Vendored XCFramework
  s.vendored_frameworks = 'KotstoneKit.xcframework'

  # Platforms
  s.ios.deployment_target     = '14.0'
  s.tvos.deployment_target    = '14.0'
  s.watchos.deployment_target = '7.0'
  s.osx.deployment_target     = '12.0'

  # ARC
  s.requires_arc = true

  # SCM metadata
  s.pod_target_xcconfig = {
    'GIT_REPO' => 'https://github.com/alisalimik/kotstone.git'
  }
end