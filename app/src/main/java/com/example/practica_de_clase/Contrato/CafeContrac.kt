package com.example.practica_de_clase.Contrato

import com.example.practica_de_clase.Modelo.VariedadCafe

interface CafeContrac {


    /**
     * Contrato principal que define las interfaces (View y Presenter)
     * para ambas pantallas: Main (selección de café) y Detail (compra).
     */
    interface CafeContract {

        // --- Pantalla Principal (Selección de Café) ---
        interface MainView {
            fun mostrarListaDeCafes(cafeNames: List<String>)
            fun navegarADetalles(cafeId: Int)
        }

        interface MainPresenter {
            fun iniciarCargaDeCafes()
            fun cafeSeleccionado(position: Int)
            fun verDetallesPresionado()
        }

        // --- Pantalla de Detalles (Compra) ---
        interface DetailView {
            fun mostrarDetallesDeCafe(cafe: VariedadCafe)
            fun mostrarError(mensaje: String)
            fun ocultarError()
            fun mostrarTotalAPagar(total: String)
        }

        interface DetailPresenter {
            fun iniciarCargaDeDetalles(cafeId: Int)
            fun cantidadAComprarCambio(cantidad: Int?)
        }
    }
}