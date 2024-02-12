package org.example

import io.camunda.zeebe.client.ZeebeClient
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

class ZeebeCoworkerTest : FunSpec({
    val container = GenericContainer(DockerImageName.parse("camunda/zeebe:8.4.2"))
        .withExposedPorts(26500)
        .withEnv("JAVA_TOOL_OPTIONS","-Xms512m -Xmx512m")
        .waitingFor(Wait.forLogMessage(".*Partition-1 recovered, marking it as healthy.*\\n", 1));

    lateinit var zeebeClient: ZeebeClient

    beforeTest {
        container.start()
        val host = container.host
        val port = container.firstMappedPort
        zeebeClient = ZeebeClient.newClientBuilder()
            .gatewayAddress("$host:$port")
            .usePlaintext()
            .build()
    }

    afterTest {
        container.stop()
    }

    test("should deploy workflow") {
        val result = zeebeClient.newDeployResourceCommand()
            .addResourceFromClasspath("dinnerDecisions.dmn")
            .send()
            .join()

        result.decisions.size shouldBe 2
    }
})