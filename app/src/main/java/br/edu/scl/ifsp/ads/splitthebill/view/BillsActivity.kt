package br.edu.scl.ifsp.ads.splitthebill.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityBillsBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Payer

class BillsActivity: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    private val abb: ActivityBillsBinding by lazy {
        ActivityBillsBinding.inflate(layoutInflater)
    }

}