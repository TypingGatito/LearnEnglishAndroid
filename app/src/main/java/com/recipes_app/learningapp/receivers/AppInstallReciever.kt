package com.recipes_app.learningapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class AppInstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
//            val isOn = Settings.Global.getInt(
//                context?.contentResolver,
//                Settings.Global.AIRPLANE_MODE_ON
//            ) != 0
//
//            println("Airplane sssss $isOn")
//        }
        if (intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            // Ваш код обработки установки
            Toast.makeText(context, "Приложение обновлено или установлено", Toast.LENGTH_SHORT).show()
        }
    }

}