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
import androidx.activity.result.contract.ActivityResultContract
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

    private val originalPayerList: MutableList<Payer> = mutableListOf()

    private val payerAdapter: PayerAdapter by lazy {
        PayerAdapter(
            this,
            payerList)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)

        amb.payersListView.adapter = payerAdapter

        originalPayerList.addAll(payerList)

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addPayerMi -> {
                carl.launch(Intent(this,PayerActivity::class.java))
                true
            } else -> false
            //Adicionar chamada para tela splitBill
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
                carl.launch(editPayerIntent)
                true} else -> {false}
        }
    }

    fun updatePayerList(_payerList:MutableList<Payer>){
        payerList.clear()
        payerList.addAll(_payerList)
        payerAdapter.notifyDataSetChanged()
    }

}//Fim da class