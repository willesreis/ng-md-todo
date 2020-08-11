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

	def "should insert single step record"() {
		given: "one step object with checked and description"
		def step = [checked: false, description: "Etapa de teste 1"]

		when: "call API POST /steps"
		def response = client.post(path: "steps", body: step, requestContentType: 'application/json')

		then: "server returns 200 code status"
		response.status == 200

		and: "returns the initial id"
		response.data.text == "1"
	}

	def "should update added step record"() {
		given: "one step object with checked and description"
		def stepChecked = [id: 1, checked: true, description: "Etapa de teste 1"]

		when: "call API PUT /steps"
		def responsePut = client.put(path: "steps", body: stepChecked, requestContentType: 'application/json')

		then: "server returns 200 code status"
		responsePut.status == 200

		when: "call API GET /steps"
		def responseGet = client.get(path: "steps", query: [id: 1], requestContentType: 'application/json')

		then: "checked field is false"
		responseGet.data[0].checked == true
	}

	def "should remove step record recently added"() {
		given: "id of step object to remove"
		def idToRemove = 1

		when: "call API DELETE /steps/{id}"
		def responseDelete = client.delete(path: "steps/$idToRemove", requestContentType: 'application/json')

		then: "server returns 200 code status"
		responseDelete.status == 200

		when: "call API GET /steps"
		def responseGet = client.get(path: "steps", requestContentType: 'application/json')

		then: "no records found"
		responseGet.data.size() == 0
	}
}
