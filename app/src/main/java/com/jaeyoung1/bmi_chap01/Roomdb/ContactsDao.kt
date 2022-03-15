package com.jaeyoung1.bmi_chap01.Roomdb

import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM tb_contacts")
    fun getAll(): List<Contacts>

    @Insert
    fun insertAll(vararg contacts: Contacts)

    @Delete
    fun delete(contacts: Contacts)

    @Query("UPDATE tb_contacts SET memoText = :memoText WHERE id = :id")
    fun update(id: Long, memoText: String)
}