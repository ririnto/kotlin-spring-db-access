package r2dbc.composite.id.entity.dto

import r2dbc.composite.id.entity.table.Sample

class SampleInfo(
    val id1: String,
    val id2: String,
    val vvvvv: String
) {
    constructor(sample: Sample) : this(
        id1 = sample.id1,
        id2 = sample.id2,
        vvvvv = sample.vvvvv
    )
}
