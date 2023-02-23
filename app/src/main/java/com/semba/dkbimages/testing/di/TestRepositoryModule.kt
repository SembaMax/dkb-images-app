package com.semba.dkbimages.testing.di

import com.semba.dkbimages.data.Repository
import com.semba.dkbimages.data.di.RepositoryModule
import com.semba.dkbimages.testing.TestImagesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestRepositoryModule {

    @Binds
    abstract fun bindTestRepository(repository: TestImagesRepository): Repository
}