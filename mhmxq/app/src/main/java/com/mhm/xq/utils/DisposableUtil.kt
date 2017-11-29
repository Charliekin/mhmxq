package com.mhm.xq.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableUtil {


    companion object {
        val compositeDisposable: CompositeDisposable = CompositeDisposable()

        fun remove(disposable: Disposable) {
            compositeDisposable.remove(disposable)
        }
    }
}