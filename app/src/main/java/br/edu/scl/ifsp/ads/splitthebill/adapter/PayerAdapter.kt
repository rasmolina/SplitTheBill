package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.databinding.TilePayerBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Payer

class PayerAdapter ( context: Context,
    private val payerList: MutableList<Payer>):ArrayAdapter<Payer>(context, R.layout.tile_payer, payerList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var payer = payerList[position]
        var tpb: TilePayerBinding? = null

        var payerTileView = convertView
        if (payerTileView == null){
            tpb = TilePayerBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            payerTileView = tpb.root

            val tilePayerHolder = TilePayerHolder(tpb.nomeTextView, tpb.valorPagoTextView)
            payerTileView.tag = tilePayerHolder
        }

        (payerTileView.tag as TilePayerHolder).nameTextView.setText(payer.name)
        (payerTileView.tag as TilePayerHolder).valorPagoTextView.setText(payer.valorPago.toString())

        tpb?.nomeTextView?.setText(payer.name)
        tpb?.valorPagoTextView?.setText(payer.valorPago.toString())

        return payerTileView
    }

    private data class TilePayerHolder(val nameTextView: TextView, val valorPagoTextView: TextView){

    }

    }