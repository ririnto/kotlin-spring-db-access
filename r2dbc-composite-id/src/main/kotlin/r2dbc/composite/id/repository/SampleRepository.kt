package r2dbc.composite.id.repository

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import r2dbc.composite.id.entity.dto.SampleInfo
import r2dbc.composite.id.entity.table.Sample
import r2dbc.composite.id.entity.table.SamplePK

@Repository
interface SampleRepository : CoroutineCrudRepository<Sample, SamplePK> {
    suspend fun findAllBy(): List<SampleInfo>

    suspend fun findById1AndId2(
        id1: String,
        id2: String
    ): Sample?

    @Modifying
    @Query(
        value = """
            INSERT INTO SAMPLE(ID1, ID2, VVVVV)
            VALUES (:#{#sample.id1},
                    :#{#sample.id2},
                    :#{#sample.vvvvv})
            ON CONFLICT (ID1, ID2)
                DO UPDATE
                SET ID1 = :#{#sample.id1},
                    ID2 = :#{#sample.id2}
        """
    )
    suspend fun upsert(sample: Sample): Long
}
