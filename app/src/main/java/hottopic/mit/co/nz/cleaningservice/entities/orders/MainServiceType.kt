package hottopic.mit.co.nz.cleaningservice.entities.orders

import java.io.Serializable

data class MainServiceType(var id: Int, var type_name: String?, var subServiceTypes: MutableList<SubServiceType>?) : Serializable
