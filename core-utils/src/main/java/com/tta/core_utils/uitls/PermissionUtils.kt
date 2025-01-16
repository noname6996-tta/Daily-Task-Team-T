package com.tta.core_utils.uitls

import android.Manifest
import android.content.Context
import android.os.Build
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

object PermissionUtils {

    fun requestNotification(context: Context, callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestSinglePermission(context, Manifest.permission.POST_NOTIFICATIONS, callback)
        }
    }

    private fun requestSinglePermission(
        context: Context,
        permission: String,
        callback: () -> Unit,
        denied: (() -> Unit)? = null
    ) {
        Dexter.withContext(context)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(report: PermissionGrantedResponse?) {
                    callback()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    denied?.invoke()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    token: PermissionToken?,
                ) {
                    token?.continuePermissionRequest()
                }

            }).onSameThread().check()
    }

    private fun requestMultiPermission(
        context: Context,
        permissions: ArrayList<String>,
        callback: () -> Unit,
        denied: (() -> Unit)? = null
    ) {
        Dexter.withContext(context)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (it.areAllPermissionsGranted()) {
                            callback()
                        } else {
                            denied?.invoke()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?,
                ) {
                    token?.continuePermissionRequest()
                }
            }).onSameThread().check()
    }
}