package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

class OrderResponse(var id: Int, var user_id: Int, var date: String?, var sub_type_name: String?, var bulk_discount: Float
                    , var product_id: Int, var main_id: Int, var sub_id: Int, var sub_icon: String?, var product_name: String?
                    , var product_icon: String?, var unit_price: Float, var unit: String?, var product_quantity: Int
                    , var phone: String?, var city: String?, var suburb:String?, var street: String?, var status: Int
                    , var amount: Float, var duration: Int, var start_time: String?, var end_time: String?, var quantity: Int
                    , var feedback: String?, var rating: Int): Serializable
