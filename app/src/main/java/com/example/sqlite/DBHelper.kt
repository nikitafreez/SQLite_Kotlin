package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "people.db", null, 1) {
    val TABLE_NAME = "people_table"
    val COL2 = "NAME"
    val COL3 = "EMAIL"

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    /*
        Создание БД
    */
    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE" + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " NAME TEXT, EMAIL TEXT, TVSHOW TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
        onCreate(db)
    }

    /*
        Создаём новую запись
    */
    fun addData(
        name: String?,
        email: String?
    ) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL2, name)
        contentValues.put(COL3, email)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }
    /*
        Получить все записи
    */
    fun showData(): Cursor? {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
}