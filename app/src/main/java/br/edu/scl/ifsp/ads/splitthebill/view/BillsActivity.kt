package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.adapter.PayerAdapter
import br.edu.scl.ifsp.ads.splitthebill.controller.BillsRoomController
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityBillsBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Payer


class BillsActivity : AppCompatActivity() {

    private val bmb: ActivityBillsBinding by lazy {
        ActivityBillsBinding.inflate(layoutInflater)
    }

    private val payerList: MutableList<Payer> = mutableListOf()

    private val billsController: BillsRoomController by lazy {
        BillsRoomController(this)
    }

    private val originalPayerList: MutableList<Payer> = mutableListOf()

    private val payerAdapter: PayerAdapter by lazy {
        PayerAdapter(
            this,
            payerList)
    }

    private lateinit var barl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bmb.root)

        setSupportActionBar(bmb.toolbarIn.toolbar)

        bmb.billsListView.adapter = payerAdapter

        originalPayerList.addAll(payerList)

        // Carregar a lista de pagadores (usuários cadastrados)
        //val payerList: List<Payer> = billsController.getPayers()
        payerAdapter.addAll(payerList)

        barl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                //
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bills,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.backMainMi -> {
                barl.launch(Intent(this, MainActivity::class.java))
                true
            }else -> false
        }
    }

    private fun calcularTotalPorPessoa(payerList: List<Payer>): Double {
        var total = 0.0
        val qtdePessoas = payerList.size

        for (payer in payerList) {
            total += payer.valorPago
        }

        val totalPorPessoa = total/qtdePessoas

        return totalPorPessoa
    }
}
