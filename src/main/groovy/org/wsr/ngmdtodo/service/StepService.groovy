package org.wsr.ngmdtodo.service

import org.wsr.ngmdtodo.domain.Step

interface StepService extends CRUDService<Integer, Step> {

    String saveAndReturnId(Step step)

}