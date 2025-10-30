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
import com.example.practica_de_clase.Contrato.CafeContrac.CafeContract
import com.example.practica_de_clase.Presentador.DetailPresenter
import com.example.practica_de_clase.Modelo.VariedadCafe
import com.example.practica_de_clase.Modelo.CafeRepository

// La clase implementa el contrato DetailView
class DetailActivity : AppCompatActivity(), CafeContract.DetailView {

    private lateinit var presenter: DetailPresenter

    // Vistas - Usando los IDs correctos del XML (ej. tvTotalPagar)
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvPrecio: TextView
    private lateinit var tvStock: TextView
    private lateinit var etCantidad: EditText
    private lateinit var tvTotalPagar: TextView // ID Corregido
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail) // Asegúrate de que el XML exista

        // Referencias UI (Usando los IDs correctos del XML)
        tvNombre = findViewById(R.id.tvNombreCafe)
        tvDescripcion = findViewById(R.id.tvDescripcionCafe)
        tvPrecio = findViewById(R.id.tvPrecioCafe) // ID Corregido
        tvStock = findViewById(R.id.tvStockCafe)   // ID Corregido
        etCantidad = findViewById(R.id.etCantidad)
        tvTotalPagar = findViewById(R.id.tvTotal) // ID Corregido
        tvError = findViewById(R.id.tvError)

        // Inicializar el Presentador y Repositorio
        // NOTA: Si moviste el Presentador y Repositorio, ajusta los imports
        presenter = DetailPresenter(this, CafeRepository())

        // Recuperar el ID del café desde el Intent
        val cafeId = intent.getIntExtra("CAFE_ID", -1)

        // 🟢 CORRECCIÓN CRÍTICA: Llamar al método de carga de detalles
        presenter.iniciarCargaDeDetalles(cafeId)

        // 🟢 CORRECCIÓN: Añadir el listener para el cálculo en tiempo real
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
        // Cambiar el color del total a rojo para indicar un error
        tvTotalPagar.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
    }

    // Método que faltaba para ocultar el error
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

