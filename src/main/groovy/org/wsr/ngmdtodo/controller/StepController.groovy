package org.wsr.ngmdtodo.services

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wsr.ngmdtodo.domain.Step
import org.wsr.ngmdtodo.service.StepService

@RestController
@RequestMapping("/steps")
class StepController {

    StepService service

    StepController(StepService service) {
        this.service = service
    }

    @GetMapping
    ArrayList findAll() {
        return service.findAll()
    }

    @PostMapping
    String insert(@RequestBody Step step) {
        return service.saveAndReturnId(step)
    }

    @PutMapping
    void update(@RequestBody Step step) {
        service.save(step)
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        service.remove(id)
    }

}