package com.mhm.xq.entity

class PhotoDirectory {
    private var id: String? = null
    private var coverPath: String? = null
    private var name: String? = null
    private var dateAdded: Long? = null
    private var photos: ArrayList<Photo>? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is PhotoDirectory) return false

        val directory = o as PhotoDirectory?

        return if (id != directory!!.id) false else name == directory.name
    }

    override fun hashCode(): Int {
        var result = id!!.hashCode()
        result = 31 * result + name!!.hashCode()
        return result
    }

    fun getId(): String {
        return id!!
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getCoverPath(): String {
        return coverPath!!
    }

    fun setCoverPath(coverPath: String) {
        this.coverPath = coverPath
    }

    fun getName(): String {
        return name!!
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getDateAdded(): Long {
        return dateAdded!!
    }

    fun setDateAdded(dateAdded: Long) {
        this.dateAdded = dateAdded
    }

    fun getPhotos(): List<Photo> {
        return photos!!
    }

    fun setPhotos(photos: List<Photo>) {
        this.photos = photos as ArrayList<Photo>
    }

    fun getPhotoPaths(): List<String> {
        val paths = ArrayList<String>(photos!!.size)
        for (photo in photos!!) {
            paths.add(photo.getPath())
        }
        return paths
    }

    fun addPhoto(id: Int, path: String) {
        photos!!.add(Photo(id, path))
    }
}