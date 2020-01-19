package com.naveen.assignmenttest.models

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName


data class HitsJson(
    @SerializedName("exhaustiveNbHits")
    val exhaustiveNbHits: Boolean,
    @SerializedName("hits")
    val hits: ArrayList<Hit>,
    @SerializedName("hitsPerPage")
    val hitsPerPage: Int,
    @SerializedName("nbHits")
    val nbHits: Int,
    @SerializedName("nbPages")
    val nbPages: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("params")
    val params: String,
    @SerializedName("processingTimeMS")
    val processingTimeMS: Int,
    @SerializedName("query")
    val query: String
)

class Hit(
    @SerializedName("author")
    val author: String,
    @SerializedName("comment_text")
    val commentText: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("created_at_i")
    val createdAtI: Int,
    @SerializedName("_highlightResult")
    val highlightResult: HighlightResult,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("objectID")
    val objectID: String,
    @SerializedName("parent_id")
    val parentId: Any,
    @SerializedName("points")
    val points: Int,
    @SerializedName("story_id")
    val storyId: Any,
    @SerializedName("story_text")
    val storyText: Any,
    @SerializedName("story_title")
    val storyTitle: Any,
    @SerializedName("story_url")
    val storyUrl: Any,
    @SerializedName("_tags")
    val tags: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)

data class HighlightResult(
    @SerializedName("author")
    val author: Author,
    @SerializedName("title")
    val title: Title,
    @SerializedName("url")
    val url: Url
)

data class Author(
    @SerializedName("matchLevel")
    val matchLevel: String,
    @SerializedName("matchedWords")
    val matchedWords: List<Any>,
    @SerializedName("value")
    val value: String
)

data class Title(
    @SerializedName("matchLevel")
    val matchLevel: String,
    @SerializedName("matchedWords")
    val matchedWords: List<Any>,
    @SerializedName("value")
    val value: String
)

data class Url(
    @SerializedName("matchLevel")
    val matchLevel: String,
    @SerializedName("matchedWords")
    val matchedWords: List<Any>,
    @SerializedName("value")
    val value: String
)