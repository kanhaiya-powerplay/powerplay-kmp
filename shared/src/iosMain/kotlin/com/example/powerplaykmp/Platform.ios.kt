package com.example.powerplaykmp

import platform.Foundation.NSUserDefaults
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion + (NSUserDefaults.standardUserDefaults.objectForKey("selectedProjectName") as? String ?: "Unknown")
}

actual fun getPlatform(): Platform = IOSPlatform()