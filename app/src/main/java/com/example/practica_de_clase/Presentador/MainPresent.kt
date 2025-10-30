package com.example.practica_de_clase.Presentador
import com.example.practica_de_clase.Contrato.CafeContract
import com.example.practica_de_clase.Modelo.CafeRepository
import com.example.practica_de_clase.Modelo.VariedadCafe

class MainPresent(private val view: CafeContract.MainView, private val repository: CafeRepository):
    CafeContract.MainPresenter
    {
        // Lista completa de cafés obtenida del Modelo
        private var cafes: List<VariedadCafe> = emptyList()
        // El ID del café seleccionado actualmente
        private var selectedCafeId: Int? = null

        override fun iniciarCargaDeCafes() {
            // 1. Solicita la lista completa de cafés al Modelo
            cafes = repository.obtenerVariedadesDeCafe()

            // 2. Extrae solo los nombres
            val cafeNames = cafes.map { it.nombre }

            // 3. Ordena a la Vista que muestre los nombres en el Spinner
            view.mostrarListaDeCafes(cafeNames)

            // Inicialmente, selecciona el ID del primer café si la lista no está vacía
            if (cafes.isNotEmpty()) {
                selectedCafeId = cafes.first().id
            }
        }

        override fun cafeSeleccionado(position: Int) {
            if (position >= 0 && position < cafes.size) {
                // Actualiza el ID seleccionado basado en la posición del Spinner
                selectedCafeId = cafes[position].id
            }
        }

        override fun verDetallesPresionado() {
            // 1. Verifica que haya un café seleccionado
            val idToNavigate = selectedCafeId
            if (idToNavigate != null) {
                // 2. Ordena a la Vista navegar a la siguiente pantalla, pasando el ID
                view.navegarADetalles(idToNavigate)
            } else {
             }
        }
}