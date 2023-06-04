package io.asdk.publisher.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.asdk.publisher.service.ObjectService
import io.asdk.publisher.utils.CreationUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@ExtendWith(SpringExtension::class)
@WebMvcTest(GalaxiesController::class)
class RestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var objectService: ObjectService

    @Test
    fun publishGalaxyIntoSqsTest() {
        val utils = CreationUtils()
        val requestBody = utils.getGalaxy()
        val expectedResponse = 202

        Mockito.`when`(objectService.process(requestBody)).thenReturn(true)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/galaxy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(requestBody))
        )
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andReturn()

        val actualResponse = result.response.status

        Assertions.assertEquals(actualResponse, expectedResponse)
    }

}