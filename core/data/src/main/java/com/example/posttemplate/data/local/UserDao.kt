package io.github.ciriti.data.local

import java.util.concurrent.ConcurrentHashMap

interface UserDao {
    suspend fun getUserById(id: Int): UserEntity?
    suspend fun insertUser(user: UserEntity)

    companion object
}

fun UserDao.Companion.create(userTable: ConcurrentHashMap<Int, UserEntity>): UserDao =
    MockUserDao(userTable)

private class MockUserDao(
    private val userTable: ConcurrentHashMap<Int, UserEntity>
) : UserDao {

    override suspend fun getUserById(id: Int): UserEntity? {
        return userTable[id]
    }

    override suspend fun insertUser(user: UserEntity) {
        userTable[user.id] = user
    }
}
