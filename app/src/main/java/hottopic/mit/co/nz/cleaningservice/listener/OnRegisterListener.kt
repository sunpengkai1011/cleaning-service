package hottopic.mit.co.nz.cleaningservice.listener

interface OnRegisterListener: OnBaseListener {
    fun onSuccess(result: Boolean, message: String)
}