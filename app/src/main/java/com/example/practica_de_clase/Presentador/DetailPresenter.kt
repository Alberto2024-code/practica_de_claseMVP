package com.example.practica_de_clase.Presentador
import  com.example.practica_de_clase.Contrato.CafeContrac
import com.example.practica_de_clase.Modelo.CafeRepository
import com.example.practica_de_clase.Modelo.VariedadCafe
import java.text.NumberFormat
import java.util.Locale

class DetailPresenter  (private val view: CafeContrac.CafeContract.DetailView,
                        private val repository: CafeRepository
) : CafeContrac.CafeContract.DetailPresenter{

    private var currentCafe: VariedadCafe? = null

    override fun iniciarCargaDeDetalles(cafeId: Int) {

        currentCafe = repository.obtenerCafePorId(cafeId)

        if (currentCafe != null) {

            view.mostrarDetallesDeCafe(currentCafe!!)

            view.mostrarTotalAPagar(formatCurrency(0.0))
        } else {

            view.mostrarError("Error: Café no encontrado.")
            view.mostrarTotalAPagar(formatCurrency(0.0))
        }
    }

    override fun cantidadAComprarCambio(cantidad: Int?) {
        val cafe = currentCafe ?: return // Si no hay café cargado, salir
        val qty = cantidad ?: 0 // Usa 0 si el input está vacío o nulo
        var total = 0.0

        if (qty <= 0) {
            // Si la cantidad es 0 o menos, ocultar error y total 0
            view.ocultarError()
            view.mostrarTotalAPagar(formatCurrency(0.0))
            return
        }


        if (qty > cafe.stock) {

            val errorMessage = "Stock insuficiente. Máximo: ${cafe.stock}"
            view.mostrarError(errorMessage)
            view.mostrarTotalAPagar(formatCurrency(0.0)) // Mostrar total a pagar como $0.00
            return
        }

        // 2. Si la cantidad es válida (<= stock)
        // Ocultar mensaje de error
        view.ocultarError()

        // 3. Calcular Total a Pagar
        total = qty * cafe.precio

        // 4. Ordenar a la Vista que muestre el total calculado
        view.mostrarTotalAPagar(formatCurrency(total))
    }


    private fun formatCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "ES"))

        return format.format(amount)
    }
}