package mx.edu.ittepic.ladm_u4_tarea2_contentproviders_renteriareyes

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_ver_eventos.*
import java.util.*

class VerEventosActivity : AppCompatActivity() {
    /* --- REQUEST CODES---------*/
    var siPermisoCalendario = 101

    /*------- OBETENER INFORMACION DEL EVENTO----*/
    var eventos = listOf<String>(CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.DTSTART, CalendarContract.Events.DESCRIPTION).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_eventos)
        /*--------------- VERIFICAR PERMISOS------------------------------*/
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            //otorgar el permiso
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALENDAR),siPermisoCalendario)
        }else{
            verEventos()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == siPermisoCalendario){
            verEventos()
        }
    }

    /*----------------- VER LOS EVENTOS DEL CALENDARIO-------------*/
    fun verEventos(){
        /*--------------- ESPECIFICAR UN CALENDARIO---------------------------*/
        val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
        val selectionArgs: Array<String> = arrayOf("anarenteriareyes21@gmail.com", "com.google", "anarenteriareyes21@gmail.com")
        /*------------------------------------------------------------------------------------------------*/

        /*--------------- HACER CONSULTA---------------------------*/
        var cursor = contentResolver.query(
            CalendarContract.Events.CONTENT_URI!!,eventos,selection,selectionArgs,null)
        var resultado = ""
        if (cursor!!.moveToFirst()) {
            var posicionTitulo = cursor.getColumnIndex(CalendarContract.Events.TITLE)
            var posicionUbicacion = cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)
            var posicionFecha = cursor.getColumnIndex(CalendarContract.Events.DTSTART)
            var posicionDescripcion = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION)
            do {
                var dt = getShortDate(cursor.getLong(posicionFecha))
                resultado += "TITULO: " + cursor.getString(posicionTitulo) +
                        "\nUBICACION: " + cursor.getString(posicionUbicacion) +
                        "\nDESCRIPCION: " + cursor.getString(posicionDescripcion) +
                        "\nFECHA: " + dt.toString() +
                        "\n============================== \n\n"
            } while (cursor.moveToNext())
        }
        textView.setText(resultado) //mostrar en etiqueta
    }
    /*------------------------- DAR FORMATO DE FECHA( ESTANDO EN MILISEGUNDOS)-----------*/
    fun getShortDate(ts: Long?) :String {
        if(ts == null) return ""
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = ts
        return android.text.format.DateFormat.format("E, dd MMM yyyy", calendar).toString()
    }



}//class
