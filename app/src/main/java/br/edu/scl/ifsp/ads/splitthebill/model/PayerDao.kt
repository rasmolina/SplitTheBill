package br.edu.scl.ifsp.ads.splitthebill.model

interface PayerDao {

    fun createPayer(payer: Payer): Int

    fun retrievePayer(id: Int): Payer?

    fun retrievePayers(): MutableList<Payer>

    fun updatePayer(payer: Payer): Int

    fun deletePayer(payer: Payer): Int

}