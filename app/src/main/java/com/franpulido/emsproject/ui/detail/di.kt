package com.franpulido.emsproject.ui.detail

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.lang.IllegalStateException
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DetailActivityModule {

    @Provides
    @Named("allInfo")
    fun allInfoProvider(stateHandle: SavedStateHandle): Boolean =
        stateHandle.get<Boolean>(DetailActivity.ALL_INFO) ?: false
}