pluginManagement {
    repositories {
        // 阿里云镜像源（优先）
        // 阿里云镜像源（优先）
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
//        google()
//        mavenCentral()
//        gradlePluginPortal()
        // 官方源（备用）
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 阿里云镜像源（优先）
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/jcenter")
        // 官方源（备用）
        google()
        mavenCentral()
    }

}

rootProject.name = "mytestapp"
include(":app")
 