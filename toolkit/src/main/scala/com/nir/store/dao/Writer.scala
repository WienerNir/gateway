package com.nir.store.dao

trait Writer[D, R] {

  def write(document: D): R

}
