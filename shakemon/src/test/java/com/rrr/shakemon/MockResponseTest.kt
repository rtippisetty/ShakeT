package com.rrr.shakemon

import com.rrr.shakemon.utils.MockResponse
import org.junit.Test

class MockResponseTest {
    @Test
    fun `read test file`() {
        val reader = MockResponse("test")
        assert(reader.json!!.contains("name"))
        assert(reader.json!!.contains("sprite"))
    }
}