package br.edu.scl.ifsp.ads.splitthebill.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.scl.ifsp.ads.splitthebill.R
import java.sql.SQLException

class PayerDaoSQLite (context: Context): PayerDao {
    //singleton
    companion object Constant {
        private const val PAYER_DATABASE_FILE = "payers"
        private const val PAYER_TABLE = "payer"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val ITEMCOMPRA_COLUMN = "itemCompra"
        private const val VALORPAGO_COLUMN = "valorPago"

        private const val CREATE_PAYER_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $PAYER_TABLE ("+
        "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$NAME_COLUMN TEXT NOT NULL, " +
        "$ITEMCOMPRA_COLUMN TEXT NOT NULL, " +
        "$VALORPAGO_COLUMN DOUBLE NOT NULL);"
    }

    //Referência pro BD
    private val payersSqliteDatabase: SQLiteDatabase
    init{
        payersSqliteDatabase =
            context.openOrCreateDatabase(PAYER_DATABASE_FILE, MODE_PRIVATE, null)
        try {
            payersSqliteDatabase.execSQL(CREATE_PAYER_TABLE_STATEMENT)
        }
        catch (se: SQLException){
            Log.e(context.getString(R.string.app_name),se.message.toString())
        }
    }

    override fun createPayer(payer: Payer) = payersSqliteDatabase.insert(PAYER_TABLE,null,payer.toContentValues()).toInt()

    override fun retrievePayer(id: Int): Payer? {
        val cursor = payersSqliteDatabase.rawQuery("SELECT * FROM $PAYER_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val payer = if(cursor.moveToFirst()) cursor.rowToPayer() else null
        cursor.close()
        return payer
    }

    override fun retrievePayers(): MutableList<Payer> {
        val payersList = mutableListOf<Payer>()

        val cursor = payersSqliteDatabase.rawQuery("SELECT * FROM $PAYER_TABLE ORDER BY $NAME_COLUMN",null)
        while (cursor.moveToNext()){
            payersList.add(cursor.rowToPayer())
        }
        cursor.close()
        return payersList
    }

    override fun updatePayer(payer: Payer) = payersSqliteDatabase.update(
        PAYER_TABLE,
        payer.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(payer.id.toString())
    )

    override fun deletePayer(id: Int) = payersSqliteDatabase.delete(
        PAYER_TABLE,
        "$ID_COLUMN = ?",
        arrayOf(id.toString())
    )


    private fun Cursor.rowToPayer() = Payer (
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NAME_COLUMN)),
        getString(getColumnIndexOrThrow(ITEMCOMPRA_COLUMN)),
        getDouble(getColumnIndexOrThrow(VALORPAGO_COLUMN)),
    )

    private fun Payer.toContentValues() = with (ContentValues()){
        put(NAME_COLUMN, name)
        put(ITEMCOMPRA_COLUMN, itemCompra)
        put(VALORPAGO_COLUMN, valorPago)
        this
    }

}