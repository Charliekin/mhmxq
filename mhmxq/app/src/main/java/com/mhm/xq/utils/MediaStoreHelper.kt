package com.mhm.xq.utils

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.MediaStore
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import com.mhm.xq.R
import com.mhm.xq.entity.PhotoDirectory
import com.mhm.xq.ui.base.activity.BaseActivity
import java.util.*

class MediaStoreHelper {
    companion object {
        val INDEX_ALL_PHOTOS = 0
        public fun getPhotoDirs(baseActivity: BaseActivity, resultCallback: PhotosResultCallback) {
            baseActivity.supportLoaderManager.
                    initLoader(0, null, PhotoDirLoaderCallbacks(baseActivity, resultCallback))
        }

        class PhotoDirLoaderCallbacks : LoaderManager.LoaderCallbacks<Cursor> {
            var context: Context? = null
            var resultCallback: PhotosResultCallback? = null

            constructor(context: Context, resultCallback: PhotosResultCallback) {
                this.context = context
                this.resultCallback = resultCallback
            }

            override fun onLoaderReset(loader: Loader<Cursor>?) {
            }

            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return PhotoDirectoryLoader(context!!)
            }

            override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
                if (data == null) return
                val directories = ArrayList<PhotoDirectory>()
                val photoDirectoryAll = PhotoDirectory()
                photoDirectoryAll.setName(context!!.getString(R.string.all_picture))
                photoDirectoryAll.setId("ALL")

                while (data.moveToNext()) {

                    val imageId = data.getInt(data.getColumnIndexOrThrow(BaseColumns._ID))
                    val bucketId = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID))
                    val name = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))
                    val path = data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
                    if (path.startsWith("/storage/emulated/0/.wbadcache/")) {
                        continue
                    }
                    val photoDirectory = PhotoDirectory()
                    photoDirectory.setId(bucketId)
                    photoDirectory.setName(name)

                    if (!directories.contains(photoDirectory)) {
                        photoDirectory.setCoverPath(path)
                        photoDirectory.addPhoto(imageId, path)
                        photoDirectory.setDateAdded(data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)))
                        directories.add(photoDirectory)
                    } else {
                        directories[directories.indexOf(photoDirectory)].addPhoto(imageId, path)
                    }

                    photoDirectoryAll.addPhoto(imageId, path)
                }
                if (photoDirectoryAll.getPhotoPaths().isNotEmpty()) {
                    photoDirectoryAll.setCoverPath(photoDirectoryAll.getPhotoPaths()[0])
                }
                directories.add(INDEX_ALL_PHOTOS, photoDirectoryAll)
                if (resultCallback != null) {
                    resultCallback!!.onResultCallback(directories)
                }
            }
        }

    }

    interface PhotosResultCallback {
        fun onResultCallback(directories: List<PhotoDirectory>)
    }

}
