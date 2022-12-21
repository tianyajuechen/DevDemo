package com.tzy.demo.activity.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.SystemClock
import android.widget.RemoteViews
import com.tzy.demo.R

/**
 * Implementation of App Widget functionality.
 */
class DateAppWidget : AppWidgetProvider() {
    private var baseTime = 0L
    private var recordTime = 0L

    companion object {
        const val FORMAT = "mm:ss"
        const val START = "com.tzy.demo.activity.appwidget.DateAppWidget.START"
        const val PAUSE = "com.tzy.demo.activity.appwidget.DateAppWidget.PAUSE"
        const val RESUME = "com.tzy.demo.activity.appwidget.DateAppWidget.RESUME"
        const val STOP = "com.tzy.demo.activity.appwidget.DateAppWidget.STOP"
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        intent.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)?.let {
            updateState(context, intent.action, it)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.date_app_widget)
        views.setChronometer(R.id.chronometer, SystemClock.elapsedRealtime(), null, true)
        views.setOnClickPendingIntent(R.id.start, createPendingIntent(context, START, appWidgetId))
        views.setOnClickPendingIntent(R.id.pause, createPendingIntent(context, PAUSE, appWidgetId))
        views.setOnClickPendingIntent(R.id.resume, createPendingIntent(context, RESUME, appWidgetId))
        views.setOnClickPendingIntent(R.id.stop, createPendingIntent(context, STOP, appWidgetId))

        val color = listOf(Color.BLUE, Color.RED, Color.GREEN)
        views.setInt(R.id.iv,"setColorFilter", Color.RED)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun createPendingIntent(context: Context, action: String, appWidgetId: Int): PendingIntent {
        val intent = Intent(context, DateAppWidget::class.java).apply {
            this.action = action
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun updateState(context: Context, action: String?, appWidgetId: Int) {
        if (action !in listOf(START, PAUSE, RESUME, STOP) || appWidgetId == 0) return
        val views = RemoteViews(context.packageName, R.layout.date_app_widget)
        val color = listOf(Color.BLUE, Color.RED, Color.GREEN)
        views.setInt(R.id.iv,"setColorFilter", color[color.indices.random()])
        when (action) {
            START -> {
                baseTime = SystemClock.elapsedRealtime()
                views.setChronometer(R.id.chronometer, baseTime, FORMAT, true)
            }
            PAUSE -> {
                recordTime = SystemClock.elapsedRealtime() - baseTime
                views.setChronometer(R.id.chronometer, baseTime, FORMAT, false)
            }
            RESUME -> {
                baseTime = SystemClock.elapsedRealtime() - recordTime
                views.setChronometer(R.id.chronometer, baseTime, FORMAT, true)
            }
            STOP -> {
                recordTime = 0L
                views.setChronometer(R.id.chronometer, SystemClock.elapsedRealtime(), FORMAT, false)
            }
        }
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views)
    }
}