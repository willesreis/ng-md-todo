package org.wsr.ngmdtodo.service

import org.wsr.ngmdtodo.domain.Step

interface CRUDService<I, T> {

    T findOne(I id)

    ArrayList findAll()

    void save(T step)

    void remove(I id)

}