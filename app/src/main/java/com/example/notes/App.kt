package com.example.notes

import android.app.Application
import com.example.notes.database.NoteDatabase

class App : Application() {

    companion object {
        lateinit var db: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = NoteDatabase.invoke(applicationContext)

    }
}