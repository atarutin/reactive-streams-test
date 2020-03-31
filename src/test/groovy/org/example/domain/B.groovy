package org.example.domain

class B {
    Long id
    String descr

    Long parentId

    B parent
    List<B> children = new ArrayList<>()
}
