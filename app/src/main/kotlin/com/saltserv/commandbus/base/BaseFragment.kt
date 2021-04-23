package com.saltserv.commandbus.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.saltserv.commandbus.ViewModelCommand
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    val fireAuth: FirebaseAuth by inject()
    val prefs: SharedPreferences by inject()

    private lateinit var viewModelCommandsSubscription: Disposable

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelCommandsSubscription = viewModel
            .commands
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleViewModelCommand(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelCommandsSubscription.dispose()
    }

    private fun handleViewModelCommand(command: ViewModelCommand) {
        if (!(activity as BaseActivity<*>).handleViewModelCommand(command)) {
            handleCustomViewModelCommand(command)
        }
    }

    protected open fun handleCustomViewModelCommand(command: ViewModelCommand) {}
}