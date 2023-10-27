package br.edu.scl.ifsp.ads.splitthebill.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PayerRoomDao {
    companion object {
        const val PAYER_DATABASE_FILE =
            "payers_room"
        private const val PAYER_TABLE = "payer"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val ITEM_COLUMN = "item_compra"
        private const val VALOR_COLUMN = "valor_pago"
    }

    @Insert
    fun createPayer(payer: Payer)

    @Query("SELECT * FROM $PAYER_TABLE WHERE $ID_COLUMN = :id")
    fun retrievePayer(id: Int): Payer?

    @Query("SELECT * FROM $PAYER_TABLE ORNAME_CO$NAME_COLUMN")
    fun retrievePayers(): MutableList<Payer>

    @Update
    fun updatePayer(payer: Payer): Int

    @Delete
    fun deletePayer(payer: Payer): Int
}