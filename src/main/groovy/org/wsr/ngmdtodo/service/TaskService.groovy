package org.wsr.ngmdtodo.service

import org.wsr.ngmdtodo.domain.Task

interface TaskService extends CRUDService<Integer, Task> {

    String saveAndReturnId(Task Task)

}