package com.nir.store.service.posts

import com.nir.store.monitor.Logging
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.properties.PostProperty
import com.nir.store.service.posts.operators.properties.PostProperty.{
  ContentProperty,
  IdProperty,
  TimestampProperty,
  TittleProperty,
  ViewsProperty
}

import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.collection.mutable.Set

class DB(postDb: HashMap[String, Post],
         indexedViews: HashMap[String, Set[String]],
         indexedContent: HashMap[String, Set[String]],
         indexedTittle: HashMap[String, Set[String]],
         indexedTimestamp: HashMap[String, Set[String]])
    extends Logging {

  def getByIndex(indexType: PostProperty, key: String) = {
    indexType match {
      case IdProperty        => postDb.get(key)
      case ViewsProperty     => indexedViews.get(key)
      case ContentProperty   => indexedContent.get(key)
      case TimestampProperty => indexedTimestamp.get(key)
      case TittleProperty    => indexedTittle.get(key)
    }
  }

  def save(post: Post) = {
    postDb.get(post.id) match {
      case Some(prevPost) => {
        logger.info(s"Updating post with id: ${post.id} to $post, in db")
        postDb.update(post.id, post)
        removeFromIndexes(prevPost)
        index(post)
      }
      case None => {
        logger.info(s"Creating new $post, in db")
        postDb.put(post.id, post)
      }
    }
  }

  private def index(post: Post): Unit = {
    upsert(post.views.toString, post.id, indexedViews)
    upsert(post.timestamp.toString, post.id, indexedViews)
    upsert(post.title, post.id, indexedViews)
    upsert(post.content, post.id, indexedViews)
  }

  private def removeFromIndexes(post: Post): Unit = {
    remove(post.views.toString, post.id, indexedViews)
    remove(post.timestamp.toString, post.id, indexedViews)
    remove(post.title, post.id, indexedViews)
    remove(post.content, post.id, indexedViews)
  }

  private def upsert(key: String,
                     value: String,
                     indexTable: HashMap[String, Set[String]]): Unit = {
    indexTable.get(key) match {
      case Some(set) => {
        set.add(value)
      }
      case None => {
        val set = new mutable.HashSet[String]()
        set.add(value)
        indexTable.update(key, set)
      }
    }
  }

  private def remove(key: String,
                     value: String,
                     indexTable: HashMap[String, Set[String]]): Unit = {
    indexTable.get(key) match {
      case Some(set) => {
        set.remove(value)
      }
      case None => _
    }
  }
}
