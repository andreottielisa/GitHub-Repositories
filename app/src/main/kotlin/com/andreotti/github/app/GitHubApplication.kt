package com.andreotti.github.app

import android.app.Application
import com.andreotti.github.di.gitHubModule
import com.andreotti.network.di.networkModule
import com.andreotti.github.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GitHubApplication)
            modules(
                platformModule,
                networkModule,
                gitHubModule
            )
        }
    }
}