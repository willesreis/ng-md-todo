package org.wsr.ngmdtodo.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wsr.ngmdtodo.domain.Task
import org.wsr.ngmdtodo.service.TaskService

@RestController
@RequestMapping("/tasks")
class TaskController {

    TaskService service

    TaskController(TaskService service) {
        this.service = service
    }

    @GetMapping("/{id}")
    Task findOne(@PathVariable Integer id) {
        return service.findOne(id)
    }

    @GetMapping
    ArrayList findAll() {
        return service.findAll()
    }

    @PostMapping
    String insert(@RequestBody Task task) {
        return service.saveAndReturnId(task)
    }

    @PutMapping
    void update(@RequestBody Task task) {
        service.save(task)
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        service.remove(id)
    }

}