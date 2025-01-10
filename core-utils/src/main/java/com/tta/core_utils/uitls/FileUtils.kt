package com.tta.core_utils.uitls

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.core.database.getStringOrNull
import timber.log.Timber
import java.io.File

object FileUtils {
    private fun createCacheImageName(context: Context, fileExtension: String = "png"): String {
        val currentTime = TimeUtils.currentTimeString(format = "HH_mm_ss_dd_mm_yyyy")
        return "cache_image_$currentTime.${fileExtension}"
    }

    private fun createInternalImageName(context: Context, fileExtension: String = "png"): String {
        val currentTime = TimeUtils.currentTimeString(format = "HH_mm_ss_dd_mm_yyyy")
        return "internal_$currentTime.${fileExtension}"
    }

    /** Use this function to save image to cache*/
    fun saveImageToCache(context: Context, bm: Bitmap?): String? {
        if (bm == null) return null

        return try {
            val file = File(context.cacheDir, createCacheImageName(context))
            file.outputStream().use { ops ->
                bm.compress(Bitmap.CompressFormat.PNG, 100, ops)
            }
            file.absolutePath
        } catch (e: Exception) {
            Timber.e("Error save image to cache: ${e.message}")
            null
        }
    }

    /** Use this function to save image to internal storage*/
    fun saveImageToInternal(context: Context, bm: Bitmap?): String? {
        if (bm == null) return null

        return try {
            val file = File(context.filesDir, createInternalImageName(context))
            file.outputStream().use { ops ->
                bm.compress(Bitmap.CompressFormat.PNG, 100, ops)
            }
            file.absolutePath
        } catch (e: Exception) {
            Timber.e("Error save image to cache: ${e.message}")
            null
        }
    }

    /**
     * - Use this function save image to gallery
     * - All app can access and modified (if permission is granted)
     * */
    fun saveImageToGallery(context: Context, path: String?): Boolean {
        if (path == null) return false

        val isSaveSuccess = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 or higher
            saveImageToGalleryInAndroid10AndHigher(context, path)
        } else {
            // Android 9 or lower
            saveImageToGalleryInAndroid9AndLower(path)
        }

        return isSaveSuccess

    }

    fun saveImageToGalleryInAndroid10AndHigher(context: Context, path: String?, fileExtension: String = "png"): Boolean {
        if (path == null) return false
        val imageFile = File(path)

        return try {
            // Create content value contain: name, mime, path, and status pending of image
            val contentValue = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/$fileExtension")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            // Insert image to folder DIRECTORY_PICTURES, using MediaStore Api
            val resultUri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValue
            )

            // Write bitmap data to result uri
            resultUri?.let { uri ->
                context.contentResolver.openOutputStream(uri)?.use { ops ->

                    // Open ips of origin image file to write data in ops of result uri
                    imageFile.inputStream().use { ips ->
                        ips.copyTo(ops)
                    }
                }

                // Update status in android 11 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    contentValue.clear()
                    contentValue.put(MediaStore.Images.Media.IS_PENDING, 0)
                    context.contentResolver.update(uri, contentValue, null, null)
                }
            } ?: false

            true
        } catch (e: Exception) {
            Timber.e("Error save image to gallery in android 10 or higher: ${e.message}")
            false
        }
    }

    fun saveImageToGalleryInAndroid9AndLower(path: String?): Boolean {
        if (path == null) return false

        val sourceFile = File(path)
        val destinationFile = File(Environment.DIRECTORY_PICTURES, sourceFile.name)
        return try {
            destinationFile.outputStream().use { ops ->
                // Open ips of origin image to write ops of destination file
                sourceFile.inputStream().use { ips ->
                    ips.copyTo(ops)
                }
            }
            true
        } catch (e: Exception) {
            Timber.e("Error save image to gallery in android 9 or lower: ${e.message}")
            false
        }
    }

    fun deleteImageInGallery(
        context: Context,
        imageUri: Uri?,
        deleteLauncher: ActivityResultLauncher<IntentSenderRequest>,
        complete: (Boolean) -> Unit
    ) {
        if (imageUri == null) {
            complete(false)
            return
        }

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                // Android 11 or higher
                val request = MediaStore.createDeleteRequest(context.contentResolver, arrayListOf(imageUri))
                val intent = IntentSenderRequest.Builder(request).build()
                deleteLauncher.launch(intent)
            }

            Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
                // Android 10 -> delete image by ID
                context.contentResolver.query(
                    imageUri,
                    arrayOf(MediaStore.Images.Media._ID),
                    null, null, null
                )?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                        val id = cursor.getLong(idColumn)

                        // Delete image
                        val result = context.contentResolver.delete(
                            imageUri,
                            "${MediaStore.Images.Media._ID} = ?",
                            arrayOf("$id")
                        )
                        complete(result > 0)
                    }
                }
            }

            else -> {
                // Android 9 or lower
                val file = imageUri.path?.let { File(it) }
                file?.let {
                    if (it.exists()) {
                        val isSuccess = it.delete()
                        complete(isSuccess)
                    }
                }
            }
        }
    }

}