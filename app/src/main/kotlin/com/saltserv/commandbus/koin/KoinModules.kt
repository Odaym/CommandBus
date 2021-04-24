package com.saltserv.commandbus.koin

import android.content.Context
import android.telephony.TelephonyManager
import com.saltserv.commandbus.base.BaseViewModel
import com.saltserv.commandbus.util.NetworkAvailabilityChecker
import com.saltserv.commandbus.util.NetworkAvailabilityCheckerImpl
import com.saltserv.commandbus.util.ResourcesProvider
import com.saltserv.commandbus.util.ResourcesProviderImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.text.NumberFormat
import java.util.*

val application = module {
    factory {
        androidContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    single {
        NumberFormat.getInstance(Locale.US)
    }

    single<ResourcesProvider> {
        ResourcesProviderImpl(get())
    }

    single(IoScheduler) {
        Schedulers.io()
    }

    single(UiScheduler) {
        AndroidSchedulers.mainThread()
    }
}

val network = module {
    single<NetworkAvailabilityChecker> {
        NetworkAvailabilityCheckerImpl(get())
    }
}

val viewModels = module {
    single {
        BaseViewModel.Dependencies(
            resourceProvider = get(),
            ioScheduler = get(IoScheduler),
            uiScheduler = get(UiScheduler)
        )
    }
}