package com.nir

import com.nir.env.TestEnvironment

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
