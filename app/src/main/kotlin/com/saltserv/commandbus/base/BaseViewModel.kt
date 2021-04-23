package com.saltserv.commandbus.base

import android.content.res.loader.ResourcesProvider
import androidx.lifecycle.ViewModel
import com.saltserv.commandbus.CloseScreen
import com.saltserv.commandbus.ViewModelCommand
import com.saltserv.commandbus.logger.LoggerFactory
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel(dependencies: Dependencies) : ViewModel() {

    protected val resourcesProvider = dependencies.resourceProvider
    protected val logger = dependencies.loggerFactory.makeLogger(javaClass)
    protected val ioScheduler = dependencies.ioScheduler
    protected val uiScheduler = dependencies.uiScheduler

    private val subscriptions = CompositeDisposable()

    private val commandsSubject: io.reactivex.subjects.Subject<ViewModelCommand> = PublishSubject.create()
    val commands: Observable<ViewModelCommand> = commandsSubject.hide()

    open fun onBackButtonClicked() = emitCommand(CloseScreen)

    protected fun emitCommand(command: ViewModelCommand) {
        commandsSubject.onNext(command)
    }

    protected fun subscription(block: () -> Disposable) {
        subscriptions.add(block())
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    protected fun <T> Observable<T>.viewModelSubscription(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) = subscription {
        subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onNext, onError)
    }

    protected fun <T> Observable<T>.viewModelSubscription(
        onNext: (T) -> Unit
    ) = subscription {
        subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onNext)
    }

    protected fun <T> Single<T>.viewModelSubscription(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) = subscription {
        subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onSuccess, onError)
    }

    protected fun <T> Maybe<T>.viewModelSubscription(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) = subscription {
        subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onSuccess, onError)
    }

    protected fun Completable.viewModelSubscription(
        onCompleted: () -> Unit,
        onError: (Throwable) -> Unit
    ) = subscription {
        subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onCompleted, onError)
    }

    protected fun <T> Flowable<T>.viewModelSubscription(
        onNext: (T) -> Unit
    ) = subscription {
        subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onNext)
    }

    data class Dependencies(
        val resourceProvider: ResourcesProvider,
        val loggerFactory: LoggerFactory,
        val ioScheduler: Scheduler,
        val uiScheduler: Scheduler
    )
}