package com.nir

class TapOps[A](a: A) {
  def tap[U](f: A => U): A = {
    f(a)
    a
  }
}
