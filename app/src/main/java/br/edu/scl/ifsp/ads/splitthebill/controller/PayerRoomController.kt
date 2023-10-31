package br.edu.scl.ifsp.ads.splitthebill.controller

import android.os.AsyncTask
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.edu.scl.ifsp.ads.splitthebill.model.Payer
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDao
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDao.Companion.PAYER_DATABASE_FILE
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDaoDatabase
import br.edu.scl.ifsp.ads.splitthebill.view.MainActivity

class PayerRoomController(private val mainActivity: MainActivity) {
    private val payerDaoImpl: PayerRoomDao by lazy {
   /*     val MIGRATION_3_4: Migration = object : Migration(3,4){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("PRAGMA foreign_keys=off;")
                db.execSQL("DROP TABLE payer")
                db.execSQL("CREATE TABLE IF NOT EXISTS Payer (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "itemCompra TEXT NOT NULL," +
                        "valorPago REAL NOT NULL," +
                        "balanco REAL NOT NULL" +
                        ");")
            }
    }*/

        Room.databaseBuilder(
            mainActivity,
            PayerRoomDaoDatabase::class.java,
            PAYER_DATABASE_FILE
        )//.addMigrations(MIGRATION_3_4)
            .build().getPayerRoomDao()
    }

    val payerList: MutableList<Payer> = mutableListOf()

    fun insertPayer(payer: Payer){
        Thread{
            payerDaoImpl.createPayer(payer)
            getPayers()
            atualizarBalanco()
        }.start()
    }

    fun getPayer(id: Int) = payerDaoImpl.retrievePayer(id)

    private fun getPayers(){
        object: AsyncTask<Unit, Unit, MutableList<Payer>>(){
            override fun doInBackground(vararg params: Unit?): MutableList<Payer> {
                return payerDaoImpl.retrievePayers()
            }

            override fun onPostExecute(result: MutableList<Payer>?) {
                super.onPostExecute(result)
                result?.also {
                    mainActivity.runOnUiThread {
                    mainActivity.updatePayerList(result)
                    }

                }
            }
        }.execute()
    }

    fun editPayer(payer: Payer){
        Thread{
            payerDaoImpl.updatePayer(payer)
            getPayers()
            atualizarBalanco()
        }.start()
    }

    fun removePayer(payer: Payer){
        Thread{
            payerDaoImpl.deletePayer(payer)
            getPayers()
            atualizarBalanco()
        }.start()
    }

    fun atualizarBalanco(){
        val listaAtual = payerDaoImpl.retrievePayers()

        var totalGeral = listaAtual.sumOf { it.valorPago }
        val qtdPessoas = listaAtual.size
        val totalPorPessoa = totalGeral / qtdPessoas
        listaAtual.forEach{ payer ->
            payer.balanco = payer.valorPago - totalPorPessoa
        }

        mainActivity.runOnUiThread {
            mainActivity.updatePayerList(listaAtual)
        }

    }





}