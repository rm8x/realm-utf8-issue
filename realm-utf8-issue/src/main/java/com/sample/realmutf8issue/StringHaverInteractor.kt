package com.sample.realmutf8issue

import android.content.Context
import io.realm.ImportFlag
import io.realm.Realm
import io.realm.RealmConfiguration

class StringHaverInteractor(
    private val applicationContext: Context,
    private val realmConfiguration: RealmConfiguration
) {

    fun createStringHavers(
        applicationContext: Context,
        coolCallback: RealmLib.CoolCallback
    ) {
        try {
            val stringHavers = mutableListOf<StringHaver>()

            val problemString = "\uD83D"

            val obj = StringHaver(
                // BEWARE
//                problemString
                realmHackBadUtfConversion(problemString)
            )

            stringHavers.add(obj)

            // batch write transaction
            Realm.getInstance(realmConfiguration).use { realm ->
                realm.refresh()
                realm.executeTransaction {
                    it.copyToRealmOrUpdate(
                        stringHavers,
                        ImportFlag.CHECK_SAME_VALUES_BEFORE_SET
                    )
                }
            }
            coolCallback.afterCreate()

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun realmHackBadUtfConversion(badText: String): String =
        String(badText.toByteArray(Charsets.UTF_8))
}
