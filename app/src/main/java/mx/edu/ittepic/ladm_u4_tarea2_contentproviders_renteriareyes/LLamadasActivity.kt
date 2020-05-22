package mx.edu.ittepic.ladm_u4_tarea2_contentproviders_renteriareyes

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.telecom.Call
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_l_lamadas.*

class LLamadasActivity : AppCompatActivity() {

    /*---------- REQUEST CODE------*/
     var siPermisoLlamadas = 707

    /*--------- ESPECIFICAR DATOS A RECUPERAR------------*/
    var llamadas = listOf<String>(CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE).toTypedArray()

    /*
    * LLAMADAS (TYPE):
    * 1-> llamadas entrantes
    * 2-> Llamadas salientes
    * 3-> Llamadas PERDIDAS
    * 4-> Llamadas Voice Mail
    * 5-> Llamadas Rechazadas
    * 6-> Numeros Bloqueados
    *
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_l_lamadas)
        /*--------------- VERIFICAR PERMISOS------------------------------*/
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            // Si no lo tiene ---> otorgar permiso
            ActivityCompat.requestPermissions(this, Array(1){
                android.Manifest.permission.READ_CALL_LOG
            }, siPermisoLlamadas)
        }else{
            registroLlamadas()
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == siPermisoLlamadas){
            /*------------------ EL PERMISO FUE OTORGADO POR PRIMERA VEZ--------------------------*/
            registroLlamadas()
        }
    }

        fun registroLlamadas(){
        var datos = listOf<String>(CallLog.Calls.NUMBER, CallLog.Calls.TYPE).toTypedArray()
        var tipo = "3"
        var datosEnPantalla = intArrayOf(R.id.textView2,R.id.textView3)

            /*------------------ HACER CONSULTAS--------------------------*/
            var cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI!!, llamadas, CallLog.Calls.TYPE + " = ?",
            arrayOf<String>(tipo.toString()), "${CallLog.Calls.LAST_MODIFIED}")
            /*---------------------- MOSTRAR LLAMADAS PERDIDAS-----------------------------*/
        var adapter = SimpleCursorAdapter(
            applicationContext,R.layout.row_list,cursor,datos,datosEnPantalla,0)
        lista.adapter = adapter


    }
}
