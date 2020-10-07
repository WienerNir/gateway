package com.nir.store.service.posts.dao

import com.nir.store.monitor.Logging
import com.nir.store.operators.Property
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.properties.PostProperty.{
  ContentProperty,
  TimestampProperty,
  TittleProperty,
  ViewsProperty
}

import scala.collection.mutable
import scala.collection.mutable.{HashMap, Set}

class PostDb(postTable: HashMap[String, Post],
             indexedViews: HashMap[String, Set[String]],
             indexedContent: HashMap[String, Set[String]],
             indexedTittle: HashMap[String, Set[String]],
             indexedTimestamp: HashMap[String, Set[String]])
    extends DB
    with Logging {

  override def getPosts(): Iterable[Post] = postTable.values

  override def getByIndex(indexType: Property,
                          key: String): Predef.Set[Post] = {
    val result = indexType match {
      case ViewsProperty     => indexedViews.get(key)
      case ContentProperty   => indexedContent.get(key.replaceAll("\"", ""))
      case TimestampProperty => indexedTimestamp.get(key)
      case TittleProperty    => indexedTittle.get(key.replaceAll("\"", ""))
    }
    result.getOrElse(mutable.Set.empty).flatMap(getById).toSet
  }

  override def getById(key: String): Option[Post] = {
    postTable.get(key.replaceAll("\"", ""))
  }

  override def save(post: Post): Unit = {
    postTable.get(post.id) match {
      case Some(prevPost) => {
        logger.info(s"Updating post with id: ${post.id} to $post, in db")
        postTable.update(post.id, post)
        removeFromIndexes(prevPost)
        index(post)
      }
      case None => {
        logger.info(s"Creating new $post, in db")
        postTable.put(post.id, post)
        index(post)
      }
    }
  }

  private def index(post: Post): Unit = {
    upsert(post.views.toString, post.id, indexedViews)
    upsert(post.timestamp.toString, post.id, indexedTimestamp)
    upsert(post.title, post.id, indexedTittle)
    upsert(post.content, post.id, indexedContent)
  }

  private def removeFromIndexes(post: Post): Unit = {
    remove(post.views.toString, post.id, indexedViews)
    remove(post.timestamp.toString, post.id, indexedTimestamp)
    remove(post.title, post.id, indexedTittle)
    remove(post.content, post.id, indexedContent)
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
      case None => ()
    }
  }
}
