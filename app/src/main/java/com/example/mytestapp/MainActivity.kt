package com.example.mytestapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    var num: Int = 0;
    private val TAG = "CeserverRunner"
    lateinit var textView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<EditText>(R.id.editTextTextMultiLine)
        var qq = MyEcho("123")
    }

    // 静态方法
    fun MyEcho(input: String): String {
        return "STATIC:" + input;
    }

    fun go(view: View) {

        num += 100;
        (view as Button).text = num.toString()
    }

    fun gox64(view: View) {
        startCeserver("ceserverx64")
        if (view is Button) {
            val btn = view
            btn.text = btn.text.toString().replace("启动", "运行中")
        }
    }

    fun goarm64(view: View) {


        startCeserver("ceserver")
        if (view is Button) {
            val btn = view
            btn.text = btn.text.toString().replace("启动", "运行中")
        }
    }

    fun gofarm64(view: View) {


        startCeserver("fridaserver")
        if (view is Button) {
            val btn = view
            btn.text = btn.text.toString().replace("启动", "运行中")
        }
    }

    // 点击按钮或其他地方调用
    fun startCeserver(assetName: String) {
//// 推荐直接用 System.loadLibrary
//        System.loadLibrary("ceserver")
//// 或者用你原来的 fullPath:
//        val path = applicationInfo.nativeLibraryDir + "/libceserver.so"
//        System.load(path)


        lifecycleScope.launch {
            //  val assetName =  // assets 下的文件名
//            val file = installAssetToFilesDir(this@MainActivity, assetName)

            // 使用 sh -c 执行（相对路径 ./ceserver，工作目录设置为 filesDir）
//            val cmd = arrayOf("sh", "-c", "./$assetName")
            var cmd = arrayOf("sh", "./$assetName")

            var path =
                this@MainActivity.getApplicationInfo().nativeLibraryDir + "/lib" + assetName + ".so";
            withContext(Dispatchers.Main) {
                textView.append("\n" + assetName + "运行中。。。。。\n$path")
                // 滚动到底部
//                    textView.post {
//                        textView.scrollTo(0, textView.layout.getLineTop(textView.lineCount) - textView.height)
//                    }

            }
            cmd = arrayOf("sh", "-c", path)
//            val process = Runtime.getRuntime().exec(path)

// 获取 PID

//            withContext(Dispatchers.Main) {
//                textView.append("\n$process")
//            }
////            Runtime.getRuntime().exec("/data/local/tmp/ceserverarm64")
//
//            return@launch

            try {
                val pb = ProcessBuilder(*cmd)
                    .directory(filesDir)          // 工作目录非常重要：让 ./ceserver 成功
                    .redirectErrorStream(true)    // 合并 stderr 到 stdout
                val proc = pb.start()

//                // 尝试拿 pid（Android O+ / Java 9+）
//                val pid = try {
//                    val m = proc::class.java.getMethod("pid")
//                    (m.invoke(proc) as? Number)?.toLong()
//                } catch (e: Exception) {
//                    null
//                }

                Log.i(TAG, "started $proc")

                // 回到主线程更新 UI
                withContext(Dispatchers.Main) {
                    textView.append("\n" + assetName + "运行中。。。。。\nstarted $proc")
                    // 滚动到底部
//                    textView.post {
//                        textView.scrollTo(0, textView.layout.getLineTop(textView.lineCount) - textView.height)
//                    }

                }

                // 异步读取输出
                withContext(Dispatchers.IO) {
                    proc.inputStream.bufferedReader().useLines { lines ->
                        lines.forEach { line ->

                            Log.i(TAG, assetName + ": $line")
//                            textView.append("\nceserver: $line")
//                            // 滚动到底部
//                            textView.post {
//                                textView.scrollTo(0, textView.layout.getLineTop(textView.lineCount) - textView.height)
//                            }

                        }
                    }
                }

                val exitCode = proc.waitFor()
                Log.i(TAG, assetName + " exitCode=$exitCode")
                // 回到主线程更新 UI
                withContext(Dispatchers.Main) {
//                    textView.text = textView.text.toString() + ("\nceserver启动失败")
                    textView.append("\n" + assetName + "启动失败")
                    // 滚动到底部
//                    textView.post {
//                        textView.scrollTo(0, textView.layout.getLineTop(textView.lineCount) - textView.height)
//                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "start failed", e)
            }
        }
    }

    private fun listFilesDirPermissions(context: Context) {
        val filesDir = context.filesDir
        if (filesDir.exists() && filesDir.isDirectory) {
            filesDir.listFiles()?.forEach { file ->
                val perms = StringBuilder().apply {
                    append(if (file.canRead()) "r" else "-")
                    append(if (file.canWrite()) "w" else "-")
                    append(if (file.canExecute()) "x" else "-")
                }
                Log.i(TAG, "${file.name} : $perms")

            }
        }
    }

    /** 将 assets 中文件复制到 app 私有目录，并确保可执行 */
    private fun installAssetToFilesDir(context: Context, assetName: String): File {

        var path = this@MainActivity.getApplicationInfo().nativeLibraryDir + "/libceserver.so";
        val outFile2 = File(context.filesDir, "ceserverarm643")


//        var mf="/data/local/tmp"
///data/user/0/com.example.mytestapp/files/profileInstalled
//        val outFile1 = File(context.filesDir, "profileInstalled")
//        outFile1.setExecutable(true, true)
        // 如果文件存在，先删除
//        if (outFile1.exists()) {
//            outFile1.delete()
//        }
        val outFile = File(context.filesDir, assetName)

        // 如果文件存在，先删除


//        val outFile = File(context.filesDir, assetName)
        context.assets.open(assetName).use { input ->
            FileOutputStream(outFile, false).use { output ->  // false 表示覆盖
                input.copyTo(output)
            }
        }
        // 设置可执行
        outFile.setExecutable(true, true)
        try {
            Runtime.getRuntime().exec(arrayOf("chmod", "777", outFile.absolutePath)).waitFor()
        } catch (e: Exception) {
            Log.w(TAG, "chmod failed: ${e.message}")
        }



        return outFile
//        val outFile = File(context.filesDir, assetName)
//        if (!outFile.exists()) {
//            context.assets.open(assetName).use { input ->
//                FileOutputStream(outFile).use { output ->
//                    input.copyTo(output)
//                }
//            }
//            // Java 层设置执行权限
//            outFile.setExecutable(true, /* ownerOnly = */ true)
//            try {
//                // 有些设备上 setExecutable 不够用，再调用 chmod 保底
//                Runtime.getRuntime().exec(arrayOf("chmod", "777", outFile.absolutePath)).waitFor()
//            } catch (e: Exception) {
//                Log.w(TAG, "chmod failed: ${e.message}")
//            }
//        }
//        return outFile
    }
}