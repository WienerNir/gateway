package com.nir.gateway.gateway

case class Color(r: Int) {
  require(r < 50, "color r must be less than 400 ")
}
