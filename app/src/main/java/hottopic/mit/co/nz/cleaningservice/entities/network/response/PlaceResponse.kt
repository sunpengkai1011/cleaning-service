package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

import hottopic.mit.co.nz.cleaningservice.entities.map.PlaceInfo

class PlaceResponse : Serializable {
    var candidates: List<PlaceInfo>? = null
}