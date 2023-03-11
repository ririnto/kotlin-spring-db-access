package r2dbc.composite.id.service

import r2dbc.composite.id.controller.dto.SampleSaveRequest
import r2dbc.composite.id.entity.table.Sample
import r2dbc.composite.id.repository.SampleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleService(private val sampleRepository: SampleRepository) {
    @Transactional
    suspend fun save(sampleSaveRequest: SampleSaveRequest) {
        val rows: Long = this.sampleRepository.upsert(
            sample = Sample(
                id1 = sampleSaveRequest.id1,
                id2 = sampleSaveRequest.id2,
                vvvvv = sampleSaveRequest.vvvvv,
            )
        )

        if (rows != 1L) {
            throw IllegalStateException("정상 처리되지 않았습니다.")
        }
    }
}
