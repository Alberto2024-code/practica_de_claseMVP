package com.example.practica_de_clase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica_de_clase.Contrato.CafeContract


import com.example.practica_de_clase.Modelo.CafeRepository
import com.example.practica_de_clase.Presentador.MainPresent

// Implementa la interfaz MainView dentro de CafeContract
class MainActivity : AppCompatActivity(), CafeContract.CafeContract.MainView{


    private lateinit var presenter: MainPresent
    private lateinit var spinnerCafes: Spinner
    private lateinit var btnVerDetalles: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        spinnerCafes = findViewById(R.id.spinnerCafes)
        btnVerDetalles = findViewById(R.id.btnVerDetalles)

        // --- Crear repositorio y presenter ---
        val repository = CafeRepository()


        presenter = MainPresent(this, repository)

        // --- Cargar cafés ---
        presenter.iniciarCargaDeCafes()


        spinnerCafes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.cafeSeleccionado(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se hace nada si no hay nada seleccionado
            }
        }



        // --- Botón "Ver Detalles" ---
        btnVerDetalles.setOnClickListener {
            presenter.verDetallesPresionado()
        }
    }


    override fun mostrarListaDeCafes(cafeNames: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cafeNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCafes.adapter = adapter
    }

    override fun navegarADetalles(cafeId: Int) {
        val intent = Intent(this, CafeContract.CafeContract.DetailView::class.java)
        intent.putExtra("CAFE_ID", cafeId) // La constante "CAFE_ID" es una buena práctica
        startActivity(intent)
    }
}
