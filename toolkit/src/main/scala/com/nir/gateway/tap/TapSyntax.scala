package com.nir.gateway.tap

import scala.language.implicitConversions

trait TapSyntax {
  implicit def toTapOps[A](a: A): TapOps[A] = new TapOps[A](a)
}

object TapSyntax extends TapSyntax
