package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityPayerBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.EXTRA_PAYER
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.VIEW_PAYER
import br.edu.scl.ifsp.ads.splitthebill.model.Payer

class PayerActivity : AppCompatActivity() {

    private val apb : ActivityPayerBinding by lazy {
        ActivityPayerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apb.root)

        setSupportActionBar(apb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Dados do Pagador"

        val receivedPayer = intent.getParcelableExtra<Payer>(EXTRA_PAYER)
        receivedPayer?.let { _receivedPayer ->
            with(apb){
                nameEt.setText(_receivedPayer.name)
                nameEt.isEnabled = false
                itemCompraEt.setText(_receivedPayer.itemCompra)
                valorPagoEt.setText(_receivedPayer.valorPago.toString())
                balancoEt.setText(_receivedPayer.balanco)
            }

            val viewPayer = intent.getBooleanExtra(VIEW_PAYER,false)
            if(viewPayer){
                with(apb){
                    nameEt.isEnabled = false
                    itemCompraEt.isEnabled = false
                    valorPagoEt.isEnabled = false
                    balancoEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
            }
        }


            apb.saveBt.setOnClickListener {
                val payer = Payer(
                    id = receivedPayer?.id,
                    name = apb.nameEt.text.toString(),
                    itemCompra = apb.itemCompraEt.text.toString(),
                    valorPago = apb.valorPagoEt.text.toString().toDouble(),
                    balanco = apb.balancoEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_PAYER,payer)
                setResult(RESULT_OK,resultIntent)
                finish()
            }
        }





}