package br.edu.scl.ifsp.ads.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Payer (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @NonNull
    var name: String,
    @NonNull
    var itemCompra: String,
    @NonNull
    var valorPago: Double,
    @NonNull
    var balanco: String
) : Parcelable