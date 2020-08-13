package org.wsr.ngmdtodo.services

import java.util.logging.Logger
import org.wsr.ngmdtodo.domain.Task
import org.wsr.ngmdtodo.sql.SqlStatements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskService {

    @Autowired
    SqlStatements statement

    @GetMapping("/tasks")
    ArrayList findAll() {
        def list = [] as ArrayList
        def rows = statement.selectReturns(
            "SELECT * FROM Task"
        )
        for (r in rows) {
            list.add(new Task(id: r.id, checked: r.checked, toDay: r.toDay, toImportant: r.toImportant, description: r.description))
        }
        return list
    }

    @PostMapping("/tasks")
    String insert(@RequestBody Task task) {
        try {
            return statement.insertReturnsNewId(
                """
                INSERT INTO Task (checked, toDay, toImportant, description) 
                VALUES (${task.checked}, ${task.toDay}, ${task.toImportant}, '${task.description}')
                """
            )
        } catch (Exception e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
        }
    }

    @PutMapping("/tasks")
    void update(@RequestBody Task task) {
        try {
            statement.executeWithTransaction(
                """
                UPDATE Task SET 
                checked = ${task.checked} 
                ,toDay = ${task.toDay} 
                ,toImportant = ${task.toImportant} 
                WHERE id = ${task.id}
                """
            )
        } catch (Exception e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
        }
    }

    @DeleteMapping("/tasks/{id}")
    void delete(@PathVariable Integer id) {
        try {
            statement.executeWithTransaction(
                """
                DELETE FROM Task 
                WHERE id = ${id}
                """
            )
        } catch (Exception e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
        }
    }

}