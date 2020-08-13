package org.wsr.ngmdtodo.domain

class Task {
    Integer id
    boolean checked
    boolean toDay
    boolean toImportant
    String description
    ArrayList steps = []
}