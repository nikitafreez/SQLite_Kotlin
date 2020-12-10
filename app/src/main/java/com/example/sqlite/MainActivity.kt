package com.example.sqlite

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var peopleDB: DBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        peopleDB = DBHelper(this@MainActivity)

        btnAdd.setOnClickListener {
            addUser()
        }

        btnShow.setOnClickListener {
            showUsers()
        }
        /*
            Alert сообщение со всеми записями
        */
    }

    private fun addUser() {
        val name: String = edtName.text.toString()
        val email:String = edtEmail.text.toString()

        val insertData: Boolean = peopleDB!!.addData(name, email)

        if(insertData == true) {
            Toast.makeText(this@MainActivity, "Запись была добавлена в БД", Toast.LENGTH_LONG)
        } else {
            Toast.makeText(this@MainActivity, "Ошибка в создании записи", Toast.LENGTH_LONG)
        }
    }
    private fun showUsers() {
        val data: Cursor? = peopleDB!!.showData()

        if (data!!.getCount() == 0) {
            display("Error", "Нет данных")
            return
        }
        val buffer = StringBuffer()
        while (data.moveToNext()) {
            buffer.append(
                "ID: " + data.getString(0) + "\n"
            )
            buffer.append(
                "NAME: " + data.getString(1) + "\n"
            )
            buffer.append(
                "EMAIL: " + data.getString(2) + "\n"
            )

            display("Все пользователи", buffer.toString())
        }
    }

    private fun display(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }
}