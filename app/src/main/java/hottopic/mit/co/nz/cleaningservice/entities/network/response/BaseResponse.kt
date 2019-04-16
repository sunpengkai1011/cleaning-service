package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

/**
 * The base response entity
 */
open class BaseResponse : Serializable {
    val code: Int = 0//The response code from backend server
    val message: String = ""//The response message from backend server
}
