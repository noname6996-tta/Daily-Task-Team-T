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

    fun requestNotification(context: Context, granted: () -> Unit, denied: (() -> Unit)? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestSinglePermission(context, Manifest.permission.POST_NOTIFICATIONS, granted, denied)
        } else {
            granted()
        }
    }

    /**
     * Use this function when you want to request only ONE permission
     * @param granted: this callback use to handle logic when user granted permission
     * @param denied (optional): this callback use to handle logic when user denied permission, example: show dialog which explained reason request permission, ...
     * */
    private fun requestSinglePermission(
        context: Context,
        permission: String,
        granted: () -> Unit,
        denied: (() -> Unit)? = null
    ) {
        Dexter.withContext(context)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(report: PermissionGrantedResponse?) {
                    granted()
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

    /**
     * Use this function when you want to request 2 or more permissions
     * @param permissions: list permission, example: arrayListOf(Manifest.Permission.READ_MEDIA_IMAGE, Manifest.Permission.READ_MEDIA_VIDEO)
     * */
    private fun requestMultiPermission(
        context: Context,
        permissions: ArrayList<String>,
        granted: () -> Unit,
        denied: (() -> Unit)? = null
    ) {
        Dexter.withContext(context)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (it.areAllPermissionsGranted()) {
                            granted()
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