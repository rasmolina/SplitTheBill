package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.databinding.TilePayerBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Payer

class PayerAdapter ( context: Context,
    private val payerList: MutableList<Payer>):ArrayAdapter<Payer>(context, R.layout.tile_payer, payerList) {

    private data class TilePayerHolder(
        val nameTextView: TextView,
        val valorPagoTextView: TextView,
        val balanceteTextView: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var payer = payerList[position]
        var tpb: TilePayerBinding? = null

        var payerTileView = convertView
        if (payerTileView == null) {
            tpb = TilePayerBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            payerTileView = tpb.root

            val tilePayerHolder =
                TilePayerHolder(tpb.nomeTextView, tpb.valorPagoTextView, tpb.balanceteTextView)
            payerTileView.tag = tilePayerHolder
        }

        with(payerTileView?.tag as TilePayerHolder) {
            nameTextView.text = payer.name
            valorPagoTextView.text = "Gastou: R$ " + payer.valorPago.toString()

            if (payer.balanco < 0.0) {
                payer.balanco = (payer.balanco * -1)
                balanceteTextView.text = "Total a pagar: R$ " + payer.balanco.toString()
                balanceteTextView.setTextColor(ContextCompat.getColor(context, R.color.colorNegative))

            } else {
                balanceteTextView.text = "Total a receber: R$ " + payer.balanco.toString()
                balanceteTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPositive))

            }

            return payerTileView
        }

    }
}