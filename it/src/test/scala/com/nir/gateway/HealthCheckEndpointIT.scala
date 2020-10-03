package com.nir.gateway

import com.nir.gateway.env.TestEnvironment

class HealthCheckEndpointIT(env: TestEnvironment)
    extends BaseIntegrationTest(env) {

  it should "validate success" in {
    val response =
      env.apiClient
        .get("/healthcheck")
        .futureValue
    response.status shouldBe 200
  }

}
