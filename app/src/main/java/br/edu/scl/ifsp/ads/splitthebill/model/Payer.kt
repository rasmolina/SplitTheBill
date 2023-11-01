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
    var itemCompra2: String,
    @NonNull
    var itemCompra3: String,
    @NonNull
    var valorPago: Double,
    @NonNull
    var valorPago2: Double,
    @NonNull
    var valorPago3: Double,
    @NonNull
    var balanco: Double
) : Parcelable