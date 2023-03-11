package r2dbc.composite.id.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import r2dbc.composite.id.controller.dto.SampleFindRequest
import r2dbc.composite.id.controller.dto.SampleSaveRequest
import r2dbc.composite.id.entity.dto.SampleInfo
import r2dbc.composite.id.entity.table.Sample
import r2dbc.composite.id.repository.SampleRepository
import r2dbc.composite.id.service.SampleService

@RestController
@RequestMapping
class SampleController(
    private val sampleService: SampleService,
    private val sampleRepository: SampleRepository
) {
    @GetMapping
    suspend fun findAll(): ResponseEntity<List<SampleInfo>> {
        return ResponseEntity.ok(this.sampleRepository.findAllBy())
    }

    @PostMapping
    suspend fun save(@RequestBody sampleSaveRequest: SampleSaveRequest): ResponseEntity<Unit> {
        this.sampleService.save(sampleSaveRequest = sampleSaveRequest)

        return ResponseEntity.ok().build()
    }

    @GetMapping("{id1}/{id2}")
    suspend fun findAllById(sampleFindRequest: SampleFindRequest): ResponseEntity<SampleInfo?> {
        return ResponseEntity.ok(
            this.sampleRepository.findById1AndId2(
                id1 = sampleFindRequest.id1,
                id2 = sampleFindRequest.id2
            )?.let { sample: Sample ->
                SampleInfo(sample = sample)
            }
        )
    }
}
