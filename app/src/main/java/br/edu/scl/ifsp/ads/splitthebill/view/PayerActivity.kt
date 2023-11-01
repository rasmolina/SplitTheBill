package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.splitthebill.adapter.PayerAdapter
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

                itemCompra2Et.setText(_receivedPayer.itemCompra2)
                valorPago2Et.setText(_receivedPayer.valorPago2.toString())

                itemCompra3Et.setText(_receivedPayer.itemCompra3)
                valorPago3Et.setText(_receivedPayer.valorPago3.toString())

                balancoEt.setText(_receivedPayer.balanco.toString())
            }

            val viewPayer = intent.getBooleanExtra(VIEW_PAYER,false)
            if(viewPayer){
                with(apb){
                    nameEt.isEnabled = false
                    itemCompraEt.isEnabled = false
                    valorPagoEt.isEnabled = false
                    itemCompra2Et.isEnabled = false
                    valorPago2Et.isEnabled = false
                    itemCompra3Et.isEnabled = false
                    valorPago3Et.isEnabled = false
                    balancoEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
            }
        }


            with(apb){
                saveBt.setOnClickListener {
                    if(valorPagoEt.text.isEmpty()) valorPago2Et.setText("0.0")
                    if(valorPago2Et.text.isEmpty()) valorPago2Et.setText("0.0")
                    if(valorPago3Et.text.isEmpty()) valorPago3Et.setText("0.0")

                    val payer: Payer = Payer(
                        id = receivedPayer?.id,
                        nameEt.text.toString(),
                        itemCompraEt.text.toString(),
                        itemCompra2Et.text.toString(),
                        itemCompra3Et.text.toString(),
                        valorPagoEt.text.toString().toDouble(),
                        valorPago2Et.text.toString().toDouble(),
                        valorPago3Et.text.toString().toDouble(),
                        //calcular balanço aqui - outro controller?
                        0.0
                    )

                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_PAYER,payer)
                    setResult(RESULT_OK,resultIntent)
                    finish()
                }

            }
        }





}