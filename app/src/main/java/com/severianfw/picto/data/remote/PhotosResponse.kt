package com.severianfw.picto.data.remote

import com.google.gson.annotations.SerializedName

data class PhotosResponse(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("urls")
	val urls: ImageUrl,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("user")
	val user: User,
)

data class ImageUrl(

	@field:SerializedName("regular")
	val regular: String,

	@field:SerializedName("full")
	val full: String
)

data class ProfileImage(

	@field:SerializedName("large")
	val large: String,

	@field:SerializedName("medium")
	val medium: String
)

data class Links(

	@field:SerializedName("download")
	val download: String,

	@field:SerializedName("download_location")
	val downloadLocation: String
)

data class User(

	@field:SerializedName("profile_image")
	val profileImage: ProfileImage,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String,

)
