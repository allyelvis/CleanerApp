package com.example.cleanerapp

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cleanMemoryButton: Button = findViewById(R.id.btn_clean_memory)
        val cleanCacheButton: Button = findViewById(R.id.btn_clean_cache)
        val detectHackingToolsButton: Button = findViewById(R.id.btn_detect_hacking_tools)

        cleanMemoryButton.setOnClickListener {
            cleanMemory()
        }

        cleanCacheButton.setOnClickListener {
            cleanCache()
        }

        detectHackingToolsButton.setOnClickListener {
            detectHackingTools()
        }
    }

    private fun cleanMemory() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        runningAppProcesses?.forEach { process ->
            activityManager.killBackgroundProcesses(process.processName)
        }
        Toast.makeText(this, "Memory cleaned", Toast.LENGTH_SHORT).show()
    }

    private fun cleanCache() {
        try {
            cacheDir.deleteRecursively()
            externalCacheDir?.deleteRecursively()
            Toast.makeText(this, "Cache cleaned", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to clean cache", Toast.LENGTH_SHORT).show()
        }
    }

    private fun detectHackingTools() {
        val packageManager = packageManager
        val installedPackages = packageManager.getInstalledPackages(0)
        val knownHackingTools = listOf("com.root.tool", "com.example.hacktool")

        val detectedTools = installedPackages.filter { packageInfo ->
            knownHackingTools.contains(packageInfo.packageName)
        }

        if (detectedTools.isNotEmpty()) {
            Toast.makeText(this, "Hacking tools detected!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No hacking tools found", Toast.LENGTH_SHORT).show()
        }
    }
}
