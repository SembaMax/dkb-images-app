package com.semba.dkbimages.data.di

import com.semba.dkbimages.data.ImagesRepository
import com.semba.dkbimages.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsImagesRepository(repository: ImagesRepository): Repository
}