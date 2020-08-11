package org.wsr.ngmdtodo.services

import java.util.logging.Logger
import org.wsr.ngmdtodo.domain.Step
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
class StepService {

    @Autowired
    SqlStatements statement

    @GetMapping("/step")
    void create() {
        statement.executeWithTransaction(
            """
            CREATE TABLE IF NOT EXISTS Step (
                id INTEGER NOT NULL IDENTITY, 
                checked BOOL NULL, 
                description VARCHAR(100) NULL
            )
            """
        )
    }

    @GetMapping("/steps")
    ArrayList findAll() {
        def list = [] as ArrayList
        def rows = statement.selectReturns(
            "SELECT * FROM Step"
        )
        for (r in rows) {
            list.add(new Step(id: r.id, checked: r.checked, description: r.description))
        }
        return list
    }

    @PostMapping("/steps")
    String insert(@RequestBody Step step) {
        try {
            return statement.insertReturnsNewId(
                """
                INSERT INTO Step (checked, description) 
                VALUES (${step.checked}, '${step.description}')
                """
            )
        } catch (Exception e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
        }
    }

    @PutMapping("/steps")
    void update(@RequestBody Step step) {
        try {
            statement.executeWithTransaction(
                """
                UPDATE Step SET 
                checked = ${step.checked} 
                WHERE id = ${step.id}
                """
            )
        } catch (Exception e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
        }
    }

    @DeleteMapping("/steps/{id}")
    void delete(@PathVariable Integer id) {
        try {
            statement.executeWithTransaction(
                """
                DELETE FROM Step 
                WHERE id = ${id}
                """
            )
        } catch (Exception e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
        }
    }

}