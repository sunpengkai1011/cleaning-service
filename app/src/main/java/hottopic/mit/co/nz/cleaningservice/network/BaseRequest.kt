package hottopic.mit.co.nz.cleaningservice.network


import android.annotation.SuppressLint
import android.content.Context

import com.android.tu.loadingdialog.LoadingDialog

import hottopic.mit.co.nz.cleaningservice.network.exception.DataFormatException
import hottopic.mit.co.nz.cleaningservice.network.exception.EmptyDataException
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import hottopic.mit.co.nz.cleaningservice.utils.RetrofitUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * The base request
 */

abstract class BaseRequest<T, R, M>(context: Context) {

    protected abstract val header: Map<String, String>?

    private val url: String
        get() = "https://evening-eyrie-43949.herokuapp.com"

    protected abstract val endpointClass: Class<T>

    private var observer = PublishSubject.create<M>()

    private var endpoint: T? = null

    private val dialog: LoadingDialog = GeneralUtil.getWaitDialog(context, "waiting")

    /**
     * Get the request observer entity
     * @return
     */
    val data: PublishSubject<M>
        @SuppressLint("CheckResult")
        get() {
            dialog.show()
            endpoint = RetrofitUtil.getService(header, url, endpointClass)
            getEndpoint(endpoint)?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ r -> dealSuccess(r) }, { throwable -> dealError(throwable) })
            return observer
        }

    protected abstract fun getEndpoint(endpoint: T?): Single<R?>?

    protected abstract fun dealWithResponse(response: R): M?


    /**
     * The succeed request callback.
     * @param response
     */
    private fun dealSuccess(response: R?) {
        dialog.dismiss()
        if (response != null) {
            val model = dealWithResponse(response)
            if (model != null) {
                Timber.d("getData success return")
                if (model is List<*>) {
                    val modelList = model as List<*>?
                    if (modelList!!.isEmpty()) {
                        Timber.d("getData error: list empty")
                        dealError(EmptyDataException())
                        return
                    }
                }
                observer.onNext(model)
            } else {
                Timber.d("getData parse error")
                dealError(DataFormatException())
            }
        } else {
            Timber.d("getData error")
            dealError(EmptyDataException())
        }
    }

    /**
     * The failed request callback.
     * @param throwable
     */
    private fun dealError(throwable: Throwable) {
        dialog.dismiss()
        Timber.d(throwable, "getData error")
        observer.onError(throwable)
    }
}
