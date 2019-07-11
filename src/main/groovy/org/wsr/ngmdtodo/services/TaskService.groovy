package org.wsr.ngmdtodo.services

import org.wsr.ngmdtodo.domain.Task
import org.wsr.ngmdtodo.sql.SqlStatements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
class TaskService {

    @Autowired
    SqlStatements statement

    @GetMapping("/create")
    void create() {
        statement.executeWithTransaction(
            "CREATE TABLE IF NOT EXISTS Task (id INTEGER NOT NULL IDENTITY, checked BOOL NULL, description VARCHAR(100) NULL)",
            "INSERT INTO Task (checked, description) VALUES (false, 'Teste unchecked')",
            "INSERT INTO Task (checked, description) VALUES (true, 'Teste checked')"
        )
    }

    @GetMapping("/tasks")
    ArrayList findAll() {
        def list = [] as ArrayList
        def rows = statement.selectReturns "SELECT * FROM Task"
        for (r in rows) {
            list << new Task(id: r.id, checked: r.checked, description: r.description)
        }
        return list
    }

}