package com.example.posttemplate.profile.data.repository

import arrow.core.Either
import com.example.posttemplate.data.local.AddressEntity
import com.example.posttemplate.data.local.CompanyEntity
import com.example.posttemplate.data.local.UserDao
import com.example.posttemplate.data.local.UserEntity
import com.example.posttemplate.data.models.AddressDto
import com.example.posttemplate.data.models.CompanyDto
import com.example.posttemplate.data.models.UserDto
import com.example.posttemplate.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest {

    private val mockApiService: ApiService = mockk()
    private val mockUserDao: UserDao = mockk()

    private val userRepository: UserRepository = UserRepository.create(mockApiService, mockUserDao)

    private val userId = 1
    private val userEntity = UserEntity(
        id = userId,
        name = "John Doe",
        email = "john.doe@example.com",
        address = AddressEntity("123 Main St", "Apt 4B", "New York", "10001"),
        phone = "123-456-7890",
        website = "www.johndoe.com",
        company = CompanyEntity(
            "Doe Enterprises",
            "Innovating the future",
            "synergize scalable solutions"
        )
    )
    private val userDto = UserDto(
        id = userId,
        name = "John Doe",
        email = "john.doe@example.com",
        address = AddressDto("123 Main St", "Apt 4B", "New York", "10001"),
        phone = "123-456-7890",
        website = "www.johndoe.com",
        company = CompanyDto(
            "Doe Enterprises",
            "Innovating the future",
            "synergize scalable solutions"
        )
    )

    @Test
    fun `getUserById should return cached user if present`() = runBlocking {
        // Arrange
        coEvery { mockUserDao.getUserById(userId) } returns userEntity

        // Act
        val result = userRepository.getUserById(userId)

        // Assert
        assertEquals(Either.Right(userDto), result)
        coVerify(exactly = 0) { mockApiService.getUserById(userId) }
    }

    @Test
    fun `getUserById should fetch from remote and cache locally if not present`() = runBlocking {
        // Arrange
        coEvery { mockUserDao.getUserById(userId) } returns null
        coEvery { mockApiService.getUserById(userId) } returns userDto
        coEvery { mockUserDao.insertUser(userEntity) } returns Unit

        // Act
        val result = userRepository.getUserById(userId)

        // Assert
        assertEquals(Either.Right(userDto), result)
        coVerify { mockApiService.getUserById(userId) }
        coVerify { mockUserDao.insertUser(userEntity) }
    }

    @Test
    fun `getUserById should return an error when remote fetch fails`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Network error")
        coEvery { mockUserDao.getUserById(userId) } returns null
        coEvery { mockApiService.getUserById(userId) } throws exception

        // Act
        val result = userRepository.getUserById(userId)

        // Assert
        assert(result.isLeft())
        assertEquals(exception, (result as Either.Left).value.cause)
    }
}
