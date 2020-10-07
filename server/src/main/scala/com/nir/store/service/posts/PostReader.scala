package com.nir.store.service.posts

import com.nir.store.dao.Reader
import com.nir.store.monitor.Logging
import com.nir.store.operators.Operator
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan,
  NotEqual
}
import com.nir.store.operators.Operator.UnaryOperator.Not
import com.nir.store.service.posts.dao.{DB, PostDb}
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.NegateOperator
import com.nir.store.service.posts.operators.properties.PostProperty.IdProperty
import com.nir.store.service.posts.operators.properties.PropertyToValueMapper

import scala.concurrent.ExecutionContext

private[posts] class PostReader(db: DB)(implicit ec: ExecutionContext)
    extends Reader[Operator, Post]
    with Logging {

  override def search(request: Operator): Set[Post] =
    runSearch(request, db.getPosts.toSet)

  private def runSearch(request: Operator, set: Set[Post]): Set[Post] = {
    request match {

      case And(a, b) => {
        val resultA: Set[Post] = runSearch(a, set)
        val resultB: Set[Post] = runSearch(b, set)
        resultA.intersect(resultB)
      }

      case Or(a, b) => runSearch(a, set) ++ runSearch(b, set)

      case Not(a) => runSearch(NegateOperator.negate(a), set)

      case LessThan(property, value) =>
        set.filter(p => PropertyToValueMapper.getIntValue(property, p) < value)

      case GreaterThan(property, value) =>
        set.filter(p => PropertyToValueMapper.getIntValue(property, p) > value)

      case Equal(property, value) => {
        property match {
          case IdProperty => {
            db.getById(value) match {
              case Some(post) => Set(post)
              case None       => Set.empty
            }
          }
          case _ => db.getByIndex(property, value)
        }
      }

      case NotEqual(property, value) =>
        set.filterNot(
          p => PropertyToValueMapper.getStringValue(property, p) == value
        )

    }
  }

}

object PostReader {}
