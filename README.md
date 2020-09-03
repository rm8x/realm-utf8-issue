### Run the sample app to reproduce the crash

###  We received a crash report with the following truncated stack trace:

```
Fatal Exception: java.lang.IllegalArgumentException: Illegal Argument: Failure when converting to UTF-8; error_code = 6;  0x0050 0x0061 0x0070 0x0061 0x0020 0x0047 0x0020 0xd83c
Exception backtrace:
<backtrace not supported on this platform> in /Users/cm/Realm/realm-java/realm/realm-library/src/main/cpp/io_realm_internal_Table.cpp line 798
       at io.realm.internal.Table.nativeFindFirstString(Table.java)
       at io.realm.internal.Table.findFirstString(Table.java:583)
       at io.realm.com_coolapp_CoolObjectRealmProxy.copyOrUpdate(com_coolapp_CoolObjectRealmProxy.java:507)
       at io.realm.LibraryModuleMediator.copyOrUpdate(LibraryModuleMediator.java:105)
       at io.realm.Realm.copyOrUpdate(Realm.java:1700)
       at io.realm.Realm.copyToRealmOrUpdate(Realm.java:1296)
```

We were able to reproduce this by attempting to insert the following string into realm (line 20 of StringHaverInteractor):
`val problemString = "\uD83D"`

This is the workaround we are using at present:
`String(badText.toByteArray(Charsets.UTF_8))`