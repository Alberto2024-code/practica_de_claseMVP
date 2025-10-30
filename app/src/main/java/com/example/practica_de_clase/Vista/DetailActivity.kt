package com.example.practica_de_clase.Vista

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.practica_de_clase.R
import com.example.practica_de_clase.Contrato.CafeContract
import com.example.practica_de_clase.Presentador.DetailPresenter
import com.example.practica_de_clase.Modelo.VariedadCafe
import com.example.practica_de_clase.Modelo.CafeRepository


class DetailActivity : AppCompatActivity(), CafeContract.DetailView {

    private lateinit var presenter: DetailPresenter

    // Vistas - Usando los IDs correctos del XML (ej. tvTotalPagar)
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvPrecio: TextView
    private lateinit var tvStock: TextView
    private lateinit var etCantidad: EditText
    private lateinit var tvTotalPagar: TextView
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        tvNombre = findViewById(R.id.tvNombreCafe)
        tvDescripcion = findViewById(R.id.tvDescripcionCafe)
        tvPrecio = findViewById(R.id.tvPrecioCafe)
        tvStock = findViewById(R.id.tvStockCafe)
        etCantidad = findViewById(R.id.etCantidad)
        tvTotalPagar = findViewById(R.id.tvTotal)
        tvError = findViewById(R.id.tvError)


        presenter = DetailPresenter(this, CafeRepository())

        // Recuperar el ID del café desde el Intent
        val cafeId = intent.getIntExtra("CAFE_ID", -1)


        presenter.iniciarCargaDeDetalles(cafeId)


        etCantidad.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // Notificar al Presentador con la nueva cantidad ingresada
                val cantidad = s.toString().toIntOrNull()
                presenter.cantidadAComprarCambio(cantidad)
            }
        })
    }

    // --- Implementación del Contrato DetailView (MÉTODOS CORREGIDOS) ---

    // Nombre de la función corregido para coincidir con el contrato
    override fun mostrarDetallesDeCafe(cafe: VariedadCafe) {
        tvNombre.text = cafe.nombre
        tvDescripcion.text = cafe.descripcion
        // Mostrar precio y stock usando el formato de la Vista
        tvPrecio.text = "$${String.format("%.2f", cafe.precio)}"
        tvStock.text = "${cafe.stock} unidades"
        tvTotalPagar.text = "$0.00"
    }

    override fun mostrarError(mensaje: String) {
        tvError.text = mensaje
        tvError.visibility = View.VISIBLE

        tvTotalPagar.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
    }


    override fun ocultarError() {
        tvError.visibility = View.GONE
        // Restablecer el color del total a negro
        tvTotalPagar.setTextColor(ContextCompat.getColor(this, android.R.color.black))
    }

    // Nombre y tipo de dato de la función corregido (espera String)
    override fun mostrarTotalAPagar(total: String) {
        tvTotalPagar.text = total
    }
}

