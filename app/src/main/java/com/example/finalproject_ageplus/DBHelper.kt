package com.example.finalproject_ageplus

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "document", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table document_tbl (_id integer primary key autoincrement, document not null)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
/*
import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context,filename:String):SQLiteOpenHelper(context,filename,null,1) {

    // Singleton
    companion object {
        var dbhelper: DBHelper? = null
        fun getInstance(context: Context, filename: String): DBHelper {
            if (dbhelper == null) {
                dbhelper = DBHelper(context, filename)
            }
            return dbhelper!!
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        var sql: String = "CREATE TABLE IF NOT EXISTS MEMBER( " +
                "SEQ INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME STRING, " +
                "AGE INTEGER, " +
                "ADDRESS STRING ) "

        db?.execSQL(sql)
    }

    /*
    override fun onCreate(db: SQLiteDatabase?) {
        var sql: String = "CREATE TABLE IF NOT EXISTS MEMBER( " +
                "SEQ INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TITLE STRING, " +
                "YEAR INTEGER, " +
                "DOCUMENT STRING ) "
        db?.execSQL(sql)
    }

     */

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insert(vo: Member) {
        var sql = " INSERT INTO MEMBER(name,age,address) " +
                " VALUES('${vo.name}',${vo.age},'${vo.address}')"

        var db = this.writableDatabase
        db.execSQL(sql)

    }


    /*
    fun insert(vo: Member) {
        var sql = " INSERT INTO MEMBER(title, year, document) " +
                " VALUES('${vo.title}',${vo.year},'${vo.document}')"

        var db = this.writableDatabase
        db.execSQL(sql)

    }
     */

    /*
    fun delete(name: String) {
        var sql = " DELETE FROM MEMBER WHERE NAME = " +
                "  '${name}' "

        var db = this.writableDatabase
        db.execSQL(sql)

    }


    fun search(name: String): String {
        var sql = " SELECT SEQ, NAME, AGE, ADDRESS FROM MEMBER WHERE NAME LIKE" +
                "'${name}'"

        var db = this.writableDatabase
        var result = db.rawQuery(sql, null)

        var str: String? = ""

        while (result.moveToNext()) {
            str += " 번호: " + result.getString(result.getColumnIndex("SEQ")) + " \n " +
                    "이름: " + result.getString(result.getColumnIndex("NAME")) + " \n " +
                    "나이: " + result.getString(result.getColumnIndex("AGE")) + " \n " +
                    "거주지: " + result.getString(result.getColumnIndex("ADDRESS"))
        }

        if(str == ""){
            println("검색된 데이터가 없습니다.")
        }

        return str!!

    }

    fun revise(name: String, age: Int, address: String) {
        var sql = " UPDATE MEMBER SET AGE = '${age}' WHERE NAME ='${name}';"

        var db = this.writableDatabase
        db.execSQL(sql)

        var sql2 = " UPDATE MEMBER SET ADDRESS = '${address}' WHERE NAME ='${name}';"

        var db2 = this.writableDatabase
        db2.execSQL(sql2)

    }
    */

    fun allMember(): String {
        var sql = " SELECT * FROM MEMBER"


        var db = this.writableDatabase
        var result = db.rawQuery(sql, null)

        var str: String? = ""

        while (result.moveToNext()) {
            str +=  "_______________________" +" \n " +" \n " +
                    " 번호: " + result.getString(result.getColumnIndex("SEQ")) + " \n " +
                    "이름: " + result.getString(result.getColumnIndex("NAME")) + " \n " +
                    "나이: " + result.getString(result.getColumnIndex("AGE")) + " \n " +
                    "거주지: " + result.getString(result.getColumnIndex("ADDRESS")) + " \n "
        }


        return str!!
    }
    /*
    @SuppressLint("Range")
    fun allMember(): String {
        var sql = " SELECT * FROM MEMBER"


        var db = this.writableDatabase
        var result = db.rawQuery(sql, null)

        var str: String? = ""

        while (result.moveToNext()) {
            str +=  "_______________________" +" \n " +" \n " +
                    " 번호: " + result.getString(result.getColumnIndex("SEQ")) + " \n " +
                    "이름: " + result.getString(result.getColumnIndex("TITLE")) + " \n " +
                    "나이: " + result.getString(result.getColumnIndex("YEAR")) + " \n " +
                    "거주지: " + result.getString(result.getColumnIndex("DOCUMENT")) + " \n "
        }


        return str!!
    }
     */
}

 */

/*
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.reflect.Member


class DBHelper(context: Context) : SQLiteOpenHelper(context, "testdb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table document_tbl (_id integer primary key autoincrement, document not null)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun insert(context: Context) {
        var sql = "Insert into Member values(document);"
            // " INSERT INTO MEMBER(name,age,address) " + " VALUES('${vo.name}',${vo.age},'${vo.address}')"

        var db = this.writableDatabase
        db.execSQL(sql)

    }

    fun allMember(): String {
        var sql = " SELECT * FROM MEMBER"


        var db = this.writableDatabase
        var result = db.rawQuery(sql, null)

        var str: String? = ""

        while (result.moveToNext()) {
            str +=  "_______________________" +" \n " +" \n " +
                    " 번호: " + result.getString(result.getColumnIndex("SEQ")) + " \n " +
                    "이름: " + result.getString(result.getColumnIndex("NAME")) + " \n " +
                    "나이: " + result.getString(result.getColumnIndex("AGE")) + " \n " +
                    "거주지: " + result.getString(result.getColumnIndex("ADDRESS")) + " \n "
        }


        return str!!
    }

    /*
    fun DocumentInsert(document : String){
        var db : SQLiteDatabase = writableDatabase

        db.execSQL(
            "Insert into Member values(document);"
        )
    }

     */

    /*
    fun MemberResult(document: String) : Boolean{
        var db : SQLiteDatabase = readableDatabase
        var cursor : Cursor = db.rawQuery("select id, password from Member", null)

        while(cursor.moveToNext()){
            if(cursor.getString(1).equals(document)){
                return true
                db.close()
                break
            }else
                return false
            }
        db.close()
        return false
        }

     */
}

 */


