package com.jogayed.tempo.journal.news.data.repository

import com.jogayed.tempo.journal.news.data.local.datasource.INewsLocalDataSource
import com.jogayed.tempo.journal.news.data.local.mapper.NewsLocalDataMapper
import com.jogayed.tempo.journal.news.data.local.model.NewsLocalDataModel
import com.jogayed.tempo.journal.news.data.remote.datasource.INewsRemoteDataSource
import com.jogayed.tempo.journal.news.data.remote.mapper.NewsRemoteDataMapper
import com.jogayed.tempo.journal.news.data.remote.model.NewsApiNetworkResponse
import com.jogayed.tempo.journal.news.data.remote.model.NewsRemoteDataModel
import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams
import com.jogayed.tempo.journal.news.domain.result.NewsResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@ExperimentalCoroutinesApi
@RunWith(BlockJUnit4ClassRunner::class)
class NewsRepositoryTest {

    @MockK
    lateinit var localDataSource: INewsLocalDataSource

    @MockK
    lateinit var remoteDataSource: INewsRemoteDataSource

    @MockK
    lateinit var localDataMapper: NewsLocalDataMapper

    @MockK
    lateinit var remoteDataMapper: NewsRemoteDataMapper

    lateinit var classUnderTest: NewsRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        classUnderTest =
            NewsRepository(localDataSource, remoteDataSource, localDataMapper, remoteDataMapper)
    }

    @Test
    fun `test getLastSearchResult in case there is cached list`() = runBlockingTest {
        //Mocking
        val newsDataList = listOf(NewsLocalDataModel(), NewsLocalDataModel())
        val newsDomainList = listOf(NewsDomainModel(), NewsDomainModel())
        every { localDataMapper.mapList(any()) } returns newsDomainList
        coEvery { localDataSource.getLastSearchResult() } returns newsDataList

        //Executing
        val expectedResult = NewsResult.SuccessOfFirstPage(newsDomainList, true)
        val actualResult = classUnderTest.getLastSearchResult()

        //Asserting
        coVerify { localDataSource.getLastSearchResult() }
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test getLastSearchResult in case there nothing cached`() = runBlockingTest {
        //Mocking
        val newsDataList = emptyList<NewsLocalDataModel>()
        val newsDomainList = emptyList<NewsDomainModel>()
        every { localDataMapper.mapList(any()) } returns newsDomainList
        coEvery { localDataSource.getLastSearchResult() } returns newsDataList

        //Executing
        val expectedResult = NewsResult.Empty
        val actualResult = classUnderTest.getLastSearchResult()

        //Asserting
        coVerify { localDataSource.getLastSearchResult() }
        Assert.assertEquals(expectedResult, actualResult)
    }


    private fun prepareMocksForSearchNews(
        isEmptyResponse: Boolean = false,
        throwable: Throwable? = null
    ): List<NewsDomainModel> {
        val localNewsDataList =
            if (isEmptyResponse)
                emptyList()
            else
                listOf(NewsLocalDataModel(), NewsLocalDataModel())

        val remoteNewsDataList = if (isEmptyResponse)
            emptyList()
        else
            listOf(NewsRemoteDataModel(), NewsRemoteDataModel())


        val newsDomainList = if (isEmptyResponse)
            emptyList()
        else
            listOf(NewsDomainModel(), NewsDomainModel())

        val networkResponse = NewsApiNetworkResponse(
            status = "OK", totalResults = 10, newsList = remoteNewsDataList
        )

        coEvery { localDataMapper.reverseMapList(any()) } returns localNewsDataList
        every { remoteDataMapper.mapList(any()) } returns newsDomainList
        coEvery { localDataSource.saveSearchResult(any()) } returns Unit
        coEvery { localDataSource.deleteAll() } returns Unit
        if (throwable != null)
            coEvery { remoteDataSource.searchNews(any()) } throws throwable
        else
            coEvery { remoteDataSource.searchNews(any()) } returns networkResponse
        return newsDomainList
    }

    @Test
    fun `test searchNews in case of loading first page`() = runBlockingTest {
        //Mocking
        val newsDomainList = prepareMocksForSearchNews()

        val spyRepo = spyk(objToCopy = classUnderTest)

        //Executing
        val searchParams = NewsSearchParams(keyword = "", page = 1)
        val expectedResult = NewsResult.SuccessOfFirstPage(newsDomainList, false)
        val actualResult = spyRepo.searchNews(searchParams)

        //Asserting
        coVerify(exactly = 1) { spyRepo.saveSearchResult(newsDomainList) }
        Assert.assertEquals(expectedResult, actualResult)
    }


    @Test
    fun `test searchNews in case of loading more pages`() = runBlockingTest {
        //Mocking
        val newsDomainList = prepareMocksForSearchNews()

        val spyRepo = spyk(objToCopy = classUnderTest)

        //Executing
        val searchParams = NewsSearchParams(keyword = "", page = 2)
        val expectedResult = NewsResult.SuccessOfLoadingMore(newsDomainList, false)
        val actualResult = spyRepo.searchNews(searchParams)

        //Asserting
        coVerify(exactly = 0) { spyRepo.saveSearchResult(newsDomainList) }
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test searchNews in case of loading first page with empty list response`() =
        runBlockingTest {
            //Mocking
            val newsDomainList = prepareMocksForSearchNews(isEmptyResponse = true)

            val spyRepo = spyk(objToCopy = classUnderTest)

            //Executing
            val searchParams = NewsSearchParams(keyword = "", page = 1)
            val expectedResult = NewsResult.Empty
            val actualResult = spyRepo.searchNews(searchParams)

            //Asserting
            coVerify(exactly = 0) { spyRepo.saveSearchResult(newsDomainList) }
            Assert.assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `test searchNews in case of loading more pages with empty list response`() =
        runBlockingTest {
            //Mocking
            val newsDomainList = prepareMocksForSearchNews(isEmptyResponse = true)

            val spyRepo = spyk(objToCopy = classUnderTest)

            //Executing
            val searchParams = NewsSearchParams(keyword = "", page = 2)
            val expectedResult = NewsResult.SuccessOfLoadingMore(newsDomainList, false)
            val actualResult = spyRepo.searchNews(searchParams)

            //Asserting
            coVerify(exactly = 0) { spyRepo.saveSearchResult(newsDomainList) }
            Assert.assertEquals(expectedResult, actualResult)
        }


    @Test
    fun `test searchNews in case of loading first page with error response`() =
        runBlockingTest {
            //Mocking
            val throwable = Exception("Something went wrong")
            val newsDomainList = prepareMocksForSearchNews(throwable = throwable)
            val spyRepo = spyk(objToCopy = classUnderTest)

            //Executing
            val searchParams = NewsSearchParams(keyword = "", page = 1)
            val expectedResult = NewsResult.ErrorOfFirstPage(throwable)
            val actualResult = spyRepo.searchNews(searchParams)

            //Asserting
            coVerify(exactly = 0) { spyRepo.saveSearchResult(newsDomainList) }
            Assert.assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `test searchNews in case of loading more pages with error response`() =
        runBlockingTest {
            //Mocking
            val throwable = Exception("Something went wrong")
            val newsDomainList = prepareMocksForSearchNews(throwable = throwable)
            val spyRepo = spyk(objToCopy = classUnderTest)

            //Executing
            val searchParams = NewsSearchParams(keyword = "", page = 2)
            val expectedResult = NewsResult.ErrorOfLoadingMore(throwable)
            val actualResult = spyRepo.searchNews(searchParams)

            //Asserting
            coVerify(exactly = 0) { spyRepo.saveSearchResult(newsDomainList) }
            Assert.assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `test saveSearchResult with list of 2 items`() =
        runBlockingTest {
            //Mocking
            val newsDomainList = listOf(NewsDomainModel(), NewsDomainModel())
            val localNewsDataList = listOf(NewsLocalDataModel(), NewsLocalDataModel())
            coEvery { localDataMapper.reverseMapList(any()) } returns localNewsDataList
            coEvery { localDataSource.deleteAll() } returns Unit
            coEvery { localDataSource.saveSearchResult(any()) } returns Unit

            //Executing
            classUnderTest.saveSearchResult(newsDomainList)

            //Asserting
            coVerifySequence {
                localDataSource.deleteAll()
                localDataSource.saveSearchResult(localNewsDataList)
            }
        }

    @Test
    fun `test saveSearchResult with empty list`() =
        runBlockingTest {
            //Mocking
            val newsDomainList = emptyList<NewsDomainModel>()

            //Executing
            classUnderTest.saveSearchResult(newsDomainList)

            //Asserting
            coVerify(exactly = 0) { localDataSource.deleteAll() }
            coVerify(exactly = 0) { localDataSource.saveSearchResult(any()) }

        }
}