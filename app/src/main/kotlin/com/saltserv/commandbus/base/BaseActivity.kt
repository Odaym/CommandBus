package com.saltserv.commandbus.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.saltserv.commandbus.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.text.NumberFormat

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    val fireAuth: FirebaseAuth by inject()
    val prefs: SharedPreferences by inject()
    val nf: NumberFormat by inject()

    private lateinit var viewModelCommandsSubscription: Disposable

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelCommandsSubscription = viewModel
            .commands
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleViewModelCommand(it) }

        Timber.plant(Timber.DebugTree())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelCommandsSubscription.dispose()
    }

    open fun handleViewModelCommand(command: ViewModelCommand): Boolean {
        when (command) {
            is ToastCommand -> Toast.makeText(this, command.message, Toast.LENGTH_LONG).show()
            is CloseScreen -> finish()
            is CloseKeyboard -> closeKeyboard()
        }

        return true
    }

    private fun closeKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}