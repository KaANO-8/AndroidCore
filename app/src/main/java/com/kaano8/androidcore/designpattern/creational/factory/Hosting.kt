package com.kaano8.androidcore.com.kaano8.androidcore.designpattern.creational.factory

// 1
interface HostingPackageInterface {
    fun getServices(): List<String>
}

// 2
enum class HostingPackageType {
    STANDARD,
    PREMIUM
}

// 3
class StandardHostingPackage : HostingPackageInterface {
    override fun getServices(): List<String> {
        return listOf()
    }
}

// 4
class PremiumHostingPackage : HostingPackageInterface {
    override fun getServices(): List<String> {
        return listOf()
    }
}

// 5
object HostingPackageFactory {
    // 6
    fun getHostingFrom(type: HostingPackageType): HostingPackageInterface {
        return when (type) {
            HostingPackageType.STANDARD -> {
                StandardHostingPackage()
            }
            HostingPackageType.PREMIUM -> {
                PremiumHostingPackage()
            }
        }
    }
}