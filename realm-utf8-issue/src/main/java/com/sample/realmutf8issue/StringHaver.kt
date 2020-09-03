package com.sample.realmutf8issue

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StringHaver(
    @PrimaryKey var coolString: String = ""
) : RealmObject()
