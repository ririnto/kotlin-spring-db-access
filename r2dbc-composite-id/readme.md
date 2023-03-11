# r2dbc-composite-id

- r2dbc는 현재까지 복합키를 지원하지 않음
- 복합키를 사용하여 save 등의 동작 수행 시 JPA처럼 사용 불가능


- `fun findById(id: ID): T` 사용 불가능
    - 다른 함수를 만들어 조회에 사용하여야 함
      ```kotlin
      suspend fun findById1AndId2(
          id1: String,
          id2: String
      ): Sample?
      ```


- Persistable Interface를 구현 하여, isNew를 사용하여 persist 가능하지만, merge는 불가능(insert 이후 update 불가능)
    - persist 시(정상)
      ```sql
      INSERT
      INTO SAMPLE(ID1,
                  ID2,
                  VVVVV)
      VALUES (:id1,
              :id2,
              :vvvvv)
      ```
    - update 시(`@Id` 어노테이션이 사용된 pk만 where 조건으로 사용, id2도 where 조건이어야 함)
      ```sql
      UPDATE SAMPLE
      SET ID2   = :id2,
          VVVVV = :vvvvv
      WHERE ID1 = :id1
      ```
    - 따라서, Persistable만을 구현하는 것으로는 사용 불가능


- 별도 upsert를 생성하여 save 대체 사용
    - ```kotlin
      @Modifying
      @Query(
          value = """
              INSERT INTO SAMPLE(ID1,
                                 ID2,
                                 VVVVV)
              VALUES (:#{#sample.id1},
                      :#{#sample.id2},
                      :#{#sample.vvvvv})
              ON CONFLICT (ID1,
                           ID2)
                  DO UPDATE
                  SET ID1 = :#{#sample.id1},
                      ID2 = :#{#sample.id2}
          """
      )
      suspend fun upsert(sample: Sample): Long
      ```
    - 이 경우, EntityListener 사용 불가(`@CreatedDate`, `@LastModifiedDate` 등)
