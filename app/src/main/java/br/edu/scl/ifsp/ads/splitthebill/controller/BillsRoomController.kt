package br.edu.scl.ifsp.ads.splitthebill.controller

import android.os.AsyncTask
import androidx.room.Room
import br.edu.scl.ifsp.ads.splitthebill.model.Payer
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDao
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDao.Companion.PAYER_DATABASE_FILE
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDaoDatabase
import br.edu.scl.ifsp.ads.splitthebill.view.BillsActivity


class BillsRoomController(private val billsActivity: BillsActivity) {
    private val payerDaoImpl: PayerRoomDao by lazy {
        Room.databaseBuilder(
            billsActivity,
            PayerRoomDaoDatabase::class.java,
            PAYER_DATABASE_FILE
        ).build().getPayerRoomDao()
    }

    private fun calculaGastoTotal(payer: Payer){
        Thread{

        }

    }

    private fun calculaDivisao(payer: Payer){
        Thread{
            payerDaoImpl.createPayer(payer)
            getPayers()
        }.start()
    }


    private fun getPayers(){
        object: AsyncTask<Unit, Unit, MutableList<Payer>>(){
            override fun doInBackground(vararg params: Unit?): MutableList<Payer> {
                return payerDaoImpl.retrievePayers()
            }
        }.execute()
    }
}
