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
            }

            val viewPayer = intent.getBooleanExtra(VIEW_PAYER,false)
            if(viewPayer){
                with(apb){
                    nameEt.isEnabled = false
                    itemCompraEt.isEnabled = false
                    valorPagoEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
            }
        }

        with(apb){
            saveBt.setOnClickListener {
                val payer: Payer = Payer(
                    id = receivedPayer?.id,
                    nameEt.text.toString(),
                    itemCompraEt.text.toString(),
                    valorPagoEt.text.toString().toDouble()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_PAYER,payer)
                setResult(RESULT_OK,resultIntent)
                finish()
            }
        }

    }



}