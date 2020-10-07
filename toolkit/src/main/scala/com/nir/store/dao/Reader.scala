package com.nir.store.dao

trait Reader[A, B] {

  def search(request: A): List[B]

}
