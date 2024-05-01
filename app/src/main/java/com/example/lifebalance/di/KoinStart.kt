package com.example.lifebalance.di

import android.app.Application
import androidx.room.Room
import com.example.lifebalance.data.TodoDatabase
import com.example.lifebalance.repositories.TodoRepository
import com.example.lifebalance.repositories.TodoRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinStart : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(databaseModule, repositoryModule)
        }
    }

    private val databaseModule = module {
        single {
            Room.databaseBuilder(
                applicationContext,
                TodoDatabase::class.java,
                "todo_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    private val repositoryModule = module {
        single<TodoRepository> {
            TodoRepositoryImpl(todoDatabase = get())
        }
    }
}
