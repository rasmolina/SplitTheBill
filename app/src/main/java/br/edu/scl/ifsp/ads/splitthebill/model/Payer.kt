package br.edu.scl.ifsp.ads.splitthebill.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Payer (
    var id: Int,
    var name: String,
    var itemCompra: String,
    var valorPago: Double
) : Parcelable