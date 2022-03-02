package com.severianfw.picto.data.remote

import com.google.gson.annotations.SerializedName

data class PhotosResponse(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("urls")
	val urls: ImageUrl? = null,

	@field:SerializedName("links")
	val links: Link? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("user")
	val user: User? = null,
)

data class ImageUrl(

	@field:SerializedName("regular")
	val regular: String? = null,

	@field:SerializedName("full")
	val full: String? = null
)

data class ProfileImage(

	@field:SerializedName("large")
	val large: String? = null,

	@field:SerializedName("medium")
	val medium: String? = null
)

data class Link(

	@field:SerializedName("download")
	val download: String? = null,

	@field:SerializedName("download_location")
	val downloadLocation: String? = null
)

data class User(

	@field:SerializedName("profile_image")
	val profileImage: ProfileImage? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

)
