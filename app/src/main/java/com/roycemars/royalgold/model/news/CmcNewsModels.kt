package com.roycemars.royalgold.model.news

import com.google.gson.annotations.SerializedName

// Based on https://coinmarketcap.com/api/documentation/v1/#operation/getV1ContentLatest
data class CmcBaseResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("status")
    val status: CmcStatus
)

data class CmcStatus(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("error_message")
    val errorMessage: String?,
    @SerializedName("elapsed")
    val elapsed: Int,
    @SerializedName("credit_count")
    val creditCount: Int
)

data class CmcContentLatestData(
    @SerializedName("posts")
    val posts: List<CmcNewsArticle>,
    @SerializedName("meta")
    val meta: CmcContentMeta
)

data class CmcNewsArticle(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String?, // Often the same as title or a summary
    @SerializedName("slug")
    val slug: String,
    @SerializedName("type")
    val type: String, // e.g., "article"
    @SerializedName("url")
    val url: String, // Link to the actual article
    @SerializedName("source_name")
    val sourceName: String,
    @SerializedName("source_url")
    val sourceUrl: String?,
    @SerializedName("cover")
    val cover: String?, // URL to the cover image
    @SerializedName("published_at")
    val publishedAt: String, // Date string, e.g., "2024-03-15T10:00:00.000Z"
    @SerializedName("assets")
    val assets: List<CmcAssetMentioned> // Cryptocurrencies mentioned
    // ... add other fields if needed, like 'votes', 'comments'
)

data class CmcAssetMentioned(
    @SerializedName("asset_id")
    val assetId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("slug")
    val slug: String
)

data class CmcContentMeta(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next_cursor")
    val nextCursor: String?, // For pagination
    @SerializedName("has_next")
    val hasNext: Boolean
)