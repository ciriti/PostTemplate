package io.github.ciriti.data.local

import java.util.concurrent.ConcurrentHashMap

interface AppDatabase {
    fun postDao(): PostDao
    fun userDao(): UserDao

    companion object
}

fun AppDatabase.Companion.create(): AppDatabase = MockAppDatabase()

private class MockAppDatabase : AppDatabase {

    private val postTable = ConcurrentHashMap<Int, PostEntity>()
    private val userTable = ConcurrentHashMap<Int, UserEntity>()

    override fun postDao(): PostDao = PostDao.create(postTable)
    override fun userDao(): UserDao = UserDao.create(userTable)
}
