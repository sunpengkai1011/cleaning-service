package hottopic.mit.co.nz.cleaningservice.network

interface RequestCallBack<T> {
    fun requestCallBack(t: T)
    fun requestFailure(t: T)
}
