package org.wsr.ngmdtodo.services

import org.wsr.ngmdtodo.domain.Task
import org.wsr.ngmdtodo.sql.SqlStatements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@RestController
class TaskService {

    @Autowired
    SqlStatements statement

    @GetMapping("/create")
    void create() {
        statement.executeWithTransaction(
            "CREATE TABLE IF NOT EXISTS Task (id INTEGER NOT NULL IDENTITY, checked BOOL NULL, description VARCHAR(100) NULL)"
        )
    }

    @GetMapping("/tasks")
    ArrayList findAll() {
        def list = [] as ArrayList
        def rows = statement.selectReturns("SELECT * FROM Task")
        for (r in rows) {
            list.add(new Task(id: r.id, checked: r.checked, description: r.description))
        }
        return list
    }

    @PostMapping("/tasks")
    void insert(@RequestBody Task task) {
        try {
            statement.executeWithTransaction(
                "INSERT INTO Task (checked, description) VALUES (${task.checked}, ${task.description})"
            )
        } catch (Exception e) {
            Logger.getLogger(this.class).severe(e.getMessage())
        }
    }

}