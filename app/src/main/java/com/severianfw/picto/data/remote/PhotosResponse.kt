package com.severianfw.picto.data.remote

import com.google.gson.annotations.SerializedName

data class PhotosResponse(

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("description")
	val description: Any,

	@field:SerializedName("urls")
	val urls: ImageUrls,

	@field:SerializedName("alt_description")
	val altDescription: Any,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("likes")
	val likes: Int
)

data class ImageUrls(

	@field:SerializedName("small")
	val small: String,

	@field:SerializedName("small_s3")
	val smallS3: String,

	@field:SerializedName("thumb")
	val thumb: String,

	@field:SerializedName("raw")
	val raw: String,

	@field:SerializedName("regular")
	val regular: String,

	@field:SerializedName("full")
	val full: String
)

data class ProfileImage(

	@field:SerializedName("small")
	val small: String,

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

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("profile_image")
	val profileImage: ProfileImage,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("username")
	val username: String
)
