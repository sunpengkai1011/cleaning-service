package hottopic.mit.co.nz.cleaningservice.listener

interface OnBooleanRepListener: OnBaseListener {
    fun onSuccess(result: Boolean, message: String)
}