package com.anytask.models.dto.user

import java.util.*

data class JwtToken(
    var id: UUID?,
    var token: String?,
    var message: String?,
    var status: String?,
    var api_name: String?
) {
    constructor() : this(UUID.randomUUID(),"", "", "", "")


}