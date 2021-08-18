package com.example.memessharing

import com.example.memessharing.api.MemeApi
import com.example.memessharing.model.MemesModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.modules.junit4.PowerMockRunner
import java.lang.NullPointerException

@RunWith(PowerMockRunner::class)
class MemeModelTest {
    val memesApi = mock<MemeApi>()
    val memeModel = MemesModel(memesApi)

    @Test
    fun `test meme list api succeed usecase`() = runBlocking {
        whenever(memesApi.fetchMemesList()).thenReturn(TestMemeData.getMemeListData())
        memeModel.getMemesList().collect {
            assertEquals(it.data, TestMemeData.getMemeListData().data)
        }
    }

    @Test
    fun `test meme videos list api succeed usecase`() = runBlocking {
        whenever(memesApi.fetchMemeVideosList()).thenReturn(TestMemeData.getMemeVideoListData())
        memeModel.getMemeVideosList().collect {
            assertEquals(it.data, TestMemeData.getMemeVideoListData().data)
        }
    }

    @Test
    fun `test meme videos list api failed usecase`() = runBlocking {
        whenever(memesApi.fetchMemeVideosList()).thenThrow(NullPointerException("null pointer exception"))
        memeModel.getMemeVideosList().catch {
            assertEquals(it.message, "null pointer exception")
        }.collect { assertNull(it) }
    }

    @Test
    fun `test meme list api failed usecase`() = runBlocking {
        whenever(memesApi.fetchMemesList()).thenThrow(NullPointerException("null pointer exception"))
        memeModel.getMemesList().catch {
            assertEquals(it.message, "null pointer exception")
        }.collect { assertNull(it) }
    }
}