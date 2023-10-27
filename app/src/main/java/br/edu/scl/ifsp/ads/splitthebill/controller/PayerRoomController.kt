package br.edu.scl.ifsp.ads.splitthebill.controller

import android.os.AsyncTask
import androidx.room.Room
import br.edu.scl.ifsp.ads.splitthebill.model.Payer
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDao
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDao.Companion.PAYER_DATABASE_FILE
import br.edu.scl.ifsp.ads.splitthebill.model.PayerRoomDaoDatabase
import br.edu.scl.ifsp.ads.splitthebill.view.MainActivity

class PayerRoomController(private val mainActivity: MainActivity) {
    private val payerDaoImpl: PayerRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            PayerRoomDaoDatabase::class.java,
            PAYER_DATABASE_FILE
        ).build().getPayerRoomDao()
    }

    fun insertPayer(payer: Payer){
        Thread{
            payerDaoImpl.createPayer(payer)
            getPayers()
        }.start()
    }

    fun getPayer(id: Int) = payerDaoImpl.retrievePayer(id)

    fun getPayers(){
        object: AsyncTask<Unit, Unit, MutableList<Payer>>(){
            override fun doInBackground(vararg params: Unit?): MutableList<Payer> {
                return payerDaoImpl.retrievePayers()
            }

            override fun onPostExecute(result: MutableList<Payer>?) {
                super.onPostExecute(result)
                result?.also {
                    mainActivity.updatePayerList(result)
                }
            }
        }.execute()
    }

    fun editPayer(payer: Payer){
        Thread{
            payerDaoImpl.updatePayer(payer)
            getPayers()
        }.start()
    }

    fun removePayer(payer: Payer){
        Thread{
            payerDaoImpl.deletePayer(payer)
            getPayers()
        }.start()
    }





}