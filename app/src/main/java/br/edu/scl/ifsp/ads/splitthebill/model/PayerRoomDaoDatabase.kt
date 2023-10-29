package br.edu.scl.ifsp.ads.splitthebill.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Payer::class], version = 3)
abstract class PayerRoomDaoDatabase: RoomDatabase(){
    abstract fun getPayerRoomDao(): PayerRoomDao
}