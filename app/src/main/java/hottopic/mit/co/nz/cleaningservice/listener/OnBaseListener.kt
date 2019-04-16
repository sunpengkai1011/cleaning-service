package hottopic.mit.co.nz.cleaningservice.listener

interface OnBaseListener {
    fun onError()
    fun onNetworkError(message: String)
}