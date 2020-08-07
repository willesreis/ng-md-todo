package org.wsr.ngmdtodo

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(
	classes = ToDoApplication.class,
	webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles(value = "test")
@ContextConfiguration
class StepServiceSpec extends Specification {

	@Shared
	def client = new RESTClient("http://localhost:8080/ng-md-todo/")

	def "should create a table with name step"() {
		when: "call API GET /step"
		def response = client.get(path: "step")
		
		then: "server returns 200 code status"
		response.status == 200
	}

	def "should return a empty list of table recently created"() {
		when: "call API GET /steps"
		def response = client.get(path: "steps")
		
		then: "server returns 200 code status"
		response.status == 200
		
		and: "step list should be empty"
		response.data.size() == 0
	}

	def "insert single step record"() {
		given: "one step object with checked and description"
		def step = [checked: false, description: "Etapa de teste 1"]

		when: "call API POST /steps"
		def response = client.post(path: "steps", body: step, requestContentType: 'application/json')

		then: "server returns 200 code status"
		response.status == 200

		and: "returns the initial id"
		response.data.text == "1"
	}
}
