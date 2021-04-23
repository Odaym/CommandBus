package com.saltserv.commandbus

import android.content.Context
import android.telephony.TelephonyManager
import com.saltserv.commandbus.CommandBusApplication.Companion.appInstance
import com.saltserv.commandbus.util.NetworkAvailabilityChecker
import com.saltserv.commandbus.util.NetworkAvailabilityCheckerImpl
import com.saltserv.commandbus.util.ResourcesProvider
import com.saltserv.commandbus.util.ResourcesProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.text.NumberFormat
import java.util.*

val application = module {
    single {
        appInstance
    }

    single {
        androidContext().getSharedPreferences(
            "BooksumPrefs", Context.MODE_PRIVATE
        )
    }

    factory {
        androidContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    single {
        NumberFormat.getInstance(Locale.US)
    }

    single<ResourcesProvider> {
        ResourcesProviderImpl(get())
    }
}

val network = module {
    single<NetworkAvailabilityChecker> {
        NetworkAvailabilityCheckerImpl(get())
    }
}