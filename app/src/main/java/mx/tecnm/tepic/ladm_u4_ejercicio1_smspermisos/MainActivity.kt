package mx.tecnm.tepic.ladm_u4_ejercicio1_smspermisos

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import mx.tecnm.tepic.ladm_u4_ejercicio1_smspermisos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val siPermiso = 1
    val siPermisoReceiver = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.RECEIVE_SMS),siPermisoReceiver)
        }

        binding.button.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.SEND_SMS), siPermiso)
            }else{
                envioSMS()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == siPermiso){
            envioSMS()
        }
        if (requestCode == siPermisoReceiver){
            mensajeRecibir()
        }
    }

    private  fun mensajeRecibir(){
        AlertDialog.Builder(this)
            .setMessage("SE OTORGA RECIBIR")
            .show()
    }

    private fun envioSMS(){
        SmsManager.getDefault().sendTextMessage(binding.numero.text.toString(),null,
            binding.texto.text.toString(),null,null)
        Toast.makeText(this, "Se envió SMS",Toast.LENGTH_LONG)
            .show()
    }
}