package r2dbc.composite.id.entity.table

import java.io.Serializable

data class SamplePK(
    val id1: String,

    val id2: String
) : Serializable

class Sample(
    val id1: String,

    val id2: String,

    var vvvvv: String
)
