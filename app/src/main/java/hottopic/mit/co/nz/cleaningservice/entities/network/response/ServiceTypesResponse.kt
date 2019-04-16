package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

class ServiceTypesResponse : BaseResponse(), Serializable {
    lateinit var serviceTypes: List<ProductResponse>
}
