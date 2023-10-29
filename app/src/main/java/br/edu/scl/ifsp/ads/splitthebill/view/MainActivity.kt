package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.adapter.PayerAdapter
import br.edu.scl.ifsp.ads.splitthebill.controller.PayerRoomController
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.EXTRA_PAYER
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.VIEW_PAYER
import br.edu.scl.ifsp.ads.splitthebill.model.Payer

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val payerList: MutableList<Payer> = mutableListOf()

    private val payerController: PayerRoomController by lazy {
        PayerRoomController(this)
    }

    private lateinit var payerAdapter: PayerAdapter

    private lateinit var parl: ActivityResultLauncher<Intent>
    private fun atualizarBalanco(){
        var totalGeral = 0.0
        for (i in 0 until payerList.size){
            totalGeral += payerList[i].valorPago
        }

        val totalPorPessoa = totalGeral / payerList.size

        for(i in 0 until payerList.size){
            payerList[i].balanco = (totalPorPessoa - payerList[i].valorPago).toString()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)

        payerAdapter = PayerAdapter(this,payerList)
        amb.payersListView.adapter = payerAdapter

        payerAdapter.notifyDataSetChanged()

        parl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result -> if(result.resultCode == RESULT_OK){
            val payer = result.data?.getParcelableExtra<Payer>(EXTRA_PAYER)
            payer?.let { _payer ->
                if(payerList.any {it.id == _payer.id}){
                    payerController.editPayer(_payer)
                }else{
                    payerController.insertPayer(_payer)
                }
            }
            }
        }


        amb.payersListView.setOnItemClickListener {parent, view, position, id ->
            val payer = payerList[position]
            val viewPayerIntent = Intent(this,PayerActivity::class.java)
            viewPayerIntent.putExtra(EXTRA_PAYER, payer)
            viewPayerIntent.putExtra(VIEW_PAYER,true)
            startActivity(viewPayerIntent)
        }

        registerForContextMenu(amb.payersListView)
    } //Fim da onCreate

    override fun onResume() {
        super.onResume()
        if(payerList.size > 0) atualizarBalanco()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addPayerMi -> {
                parl.launch(Intent(this,PayerActivity::class.java))
                true
            }else -> false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position

        val payer = payerList[position]

        return when(item.itemId){
            R.id.removePayerMi -> {
                payerController.removePayer(payer)
                Toast.makeText(this, "Pagador removido!", Toast.LENGTH_SHORT).show()
                true}
            R.id.editPayerMi -> {
                val payer = payerList[position]
                val editPayerIntent = Intent(this, PayerActivity::class.java)
                editPayerIntent.putExtra(EXTRA_PAYER,payer)
                parl.launch(editPayerIntent)
                true} else -> {false}
        }
    }

    fun updatePayerList(_payerList:MutableList<Payer>){
        payerList.clear()
        payerList.addAll(_payerList)
        payerAdapter.notifyDataSetChanged()
    }

}//Fim da class