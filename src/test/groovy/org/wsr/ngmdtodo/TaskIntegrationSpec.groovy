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
class TaskIntegrationSpec extends Specification {

	@Shared
	def client = new RESTClient("http://localhost:8080/ng-md-todo/")

	def "should return a empty list of table recently created"() {
		when: "call API GET /tasks"
		def response = client.get(path: "tasks")
		
		then: "server returns 200 code status"
		response.status == 200
		
		and: "task list should be empty"
		response.data.size() == 0
	}

	def "should insert single task record"() {
		given: "one task object with checked and description"
		def task = [checked: false, description: "Tarefa de teste 1"]

		when: "call API POST /tasks"
		def response = client.post(path: "tasks", body: task, requestContentType: 'application/json')

		then: "server returns 200 code status"
		response.status == 200

		and: "returns the initial id"
		response.data.text == "1"
	}

	def "should update added task record"() {
		given: "one task object with checked and description"
		def taskChecked = [id: 1, checked: true, description: "Tarefa de teste 1"]

		when: "call API PUT /tasks"
		def responsePut = client.put(path: "tasks", body: taskChecked, requestContentType: 'application/json')

		then: "server returns 200 code status"
		responsePut.status == 200

		when: "call API GET /tasks"
		def responseGet = client.get(path: "tasks", query: [id: 1], requestContentType: 'application/json')

		then: "checked field is true"
		responseGet.data[0].checked == true
	}

	def "should remove task record recently added"() {
		given: "id of task object to remove"
		def idToRemove = 1

		when: "call API DELETE /tasks/{id}"
		def responseDelete = client.delete(path: "tasks/$idToRemove", requestContentType: 'application/json')

		then: "server returns 200 code status"
		responseDelete.status == 200

		when: "call API GET /tasks"
		def responseGet = client.get(path: "tasks", requestContentType: 'application/json')

		then: "no records found"
		responseGet.data.size() == 0
	}
}
