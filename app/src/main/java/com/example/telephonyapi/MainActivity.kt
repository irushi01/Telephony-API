package com.example.telephonyapi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val networkCountryIsoText = findViewById<TextView>(R.id.networkCountryIso)
        val simCountryIsoText = findViewById<TextView>(R.id.simCountryIso)
        val networkTypeText = findViewById<TextView>(R.id.networkType)
        val isRoamingText = findViewById<TextView>(R.id.isRoaming)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                1
            )
        } else {
            displayTelephonyInfo(
                networkCountryIsoText, simCountryIsoText, networkTypeText,
                isRoamingText
            )
        }
    }

    private fun displayTelephonyInfo(
        networkCountryIsoText: TextView,
        simCountryIsoText: TextView,
        networkTypeText: TextView,
        isRoamingText: TextView
    ) {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val networkCountryIso = telephonyManager.networkCountryIso
        val simCountryIso = telephonyManager.simCountryIso
        val networkType = getNetworkTypeString(telephonyManager.networkType)
        val isRoaming = telephonyManager.isNetworkRoaming
        networkCountryIsoText.text = "Network Country ISO: $networkCountryIso"
        simCountryIsoText.text = "SIM Country ISO: $simCountryIso"
        networkTypeText.text = "Network Type: $networkType"
        isRoamingText.text = "Roaming: ${if (isRoaming) "Yes" else "No"}"
    }

    private fun getNetworkTypeString(networkType: Int): String {
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS -> "GPRS"
            TelephonyManager.NETWORK_TYPE_EDGE -> "EDGE"
            TelephonyManager.NETWORK_TYPE_UMTS -> "UMTS"
            TelephonyManager.NETWORK_TYPE_HSDPA -> "HSDPA"
            TelephonyManager.NETWORK_TYPE_HSUPA -> "HSUPA"
            TelephonyManager.NETWORK_TYPE_HSPA -> "HSPA"
            TelephonyManager.NETWORK_TYPE_LTE -> "LTE"
            TelephonyManager.NETWORK_TYPE_NR -> "5G"
            else -> "Unknown"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED
        ) {
            displayTelephonyInfo(
                findViewById(R.id.networkCountryIso),
                findViewById(R.id.simCountryIso),
                findViewById(R.id.networkType),
                findViewById(R.id.isRoaming)
            )
        }
    }
}
