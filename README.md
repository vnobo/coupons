# qinke-coupons
Tao bao  and jing dong coupons.

## [Upgrading your build from Gradle 6.x to the latest](https://docs.gradle.org/current/userguide/upgrading_version_6.html)

* Try running `gradle help --scan` and view the deprecations view of the generated build scan.
* Update your plugins.

  Some plugins will break with this new version of Gradle, for example because they use internal APIs that have been removed or changed. The previous step will help you identify potential problems by issuing deprecation warnings when a plugin does try to use a deprecated part of the API.
* Run `gradle wrapper --gradle-version 6.3` to update the project to 6.3.
