package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

/**
 * The registration response entity.
 */
class BooleanResponse : BaseResponse(), Serializable {
    val result: Boolean = false
}
