package com.sample.realmutf8issue

import android.app.Application
import android.content.Context
import io.realm.*
import io.realm.annotations.RealmModule
import io.realm.kotlin.where

open class RealmLib private constructor() {

    interface CoolCallback {
        fun afterCreate()
    }

    val tag = "AndroidDeviceContacts"

    private lateinit var realmConfig: RealmConfiguration
    private lateinit var mainThreadRealm: Realm

    lateinit var interactor: StringHaverInteractor

    fun createAndCauseProblem(applicationContext: Context, coolCallback: CoolCallback) {
        interactor.createStringHavers(applicationContext, coolCallback)
    }

    fun readItems() : List<StringHaver> {
        return mainThreadRealm.where<StringHaver>()
            .findAll()
    }

    @Synchronized
    @Throws(IllegalStateException::class)
    private fun initializeInstance(
        applicationContext: Context
    ) {
        setupRealm(applicationContext)
        interactor = StringHaverInteractor(applicationContext, realmConfig)
    }

    // The Realm file is in Context.getFilesDir();
    private fun setupRealm(
        applicationContext: Context
    ) {
        Realm.init(applicationContext)
        realmConfig = RealmConfiguration.Builder()
            .name("utf8.realm")
            .modules(CoolBusinessModule())
            .build()

        mainThreadRealm = Realm.getInstance(realmConfig)
    }

    companion object {
        private val instance: RealmLib = RealmLib()

        @Synchronized
        @JvmStatic
        fun getInstance(): RealmLib {
            return instance
        }

        @Synchronized
        @JvmStatic
        @Throws(IllegalStateException::class)
        fun initialize(
            application: Application
        ): RealmLib {
            instance.initializeInstance(
                application
            )
            return instance
        }
    }
}

@RealmModule(library = true, allClasses = true)
class CoolBusinessModule