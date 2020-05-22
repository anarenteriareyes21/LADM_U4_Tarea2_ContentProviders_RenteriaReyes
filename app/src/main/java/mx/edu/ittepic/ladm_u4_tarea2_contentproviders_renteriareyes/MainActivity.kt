package mx.edu.ittepic.ladm_u4_tarea2_contentproviders_renteriareyes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnVerEventos.setOnClickListener {
            startActivity(Intent(this,VerEventosActivity::class.java))
        }
        btnLlamadas.setOnClickListener {
            startActivity(Intent(this,LLamadasActivity::class.java))
        }


    }
}
