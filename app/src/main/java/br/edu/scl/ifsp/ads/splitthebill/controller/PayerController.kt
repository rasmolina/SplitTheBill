package br.edu.scl.ifsp.ads.splitthebill.controller

import br.edu.scl.ifsp.ads.splitthebill.model.Payer
import br.edu.scl.ifsp.ads.splitthebill.model.PayerDao
import br.edu.scl.ifsp.ads.splitthebill.model.PayerDaoSQLite
import br.edu.scl.ifsp.ads.splitthebill.view.MainActivity

class PayerController(mainActivity: MainActivity) {
    private val payerDaoImpl: PayerDao = PayerDaoSQLite(mainActivity)

    fun insertPayer(payer: Payer) = payerDaoImpl.createPayer(payer)
    fun getPayer(id: Int) = payerDaoImpl.retrievePayer(id)
    fun getPayers() = payerDaoImpl.retrievePayers()
    fun editPayer(payer: Payer) = payerDaoImpl.updatePayer(payer)
    //fun removePayer(id: Int) = payerDaoImpl.deletePayer(id)


}