package com.aube.mysize.data.di

import android.content.Context
import com.aube.mysize.data.database.MySizeDatabase
import com.aube.mysize.data.database.dao.BrandDao
import com.aube.mysize.data.database.dao.ClothesDao
import com.aube.mysize.data.database.dao.ShopDao
import com.aube.mysize.data.database.dao.SizeDao
import com.aube.mysize.data.database.dao.UserDao
import com.aube.mysize.data.datastore.SettingsDataStore
import com.aube.mysize.data.model.entity.size.SizeEntity
import com.aube.mysize.data.repository.BlockedUserRepositoryImpl
import com.aube.mysize.data.repository.BrandRepositoryImpl
import com.aube.mysize.data.repository.ClothesRepositoryImpl
import com.aube.mysize.data.repository.FollowRepositoryImpl
import com.aube.mysize.data.repository.ReportRepositoryImpl
import com.aube.mysize.data.repository.ShopRepositoryImpl
import com.aube.mysize.data.repository.SizeRepositoryImpl
import com.aube.mysize.data.repository.UserRepositoryImpl
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.repository.BlockedUserRepository
import com.aube.mysize.domain.repository.BrandRepository
import com.aube.mysize.domain.repository.ClothesRepository
import com.aube.mysize.domain.repository.FollowRepository
import com.aube.mysize.domain.repository.ReportRepository
import com.aube.mysize.domain.repository.ShopRepository
import com.aube.mysize.domain.repository.SizeRepository
import com.aube.mysize.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideSizeRepository(
        firestore: FirebaseFirestore,
        daoMap: Map<SizeCategory, @JvmSuppressWildcards SizeDao<out SizeEntity>>
    ): SizeRepository = SizeRepositoryImpl(firestore, daoMap)

    @Provides
    fun provideBrandRepository(
        dao: BrandDao
    ): BrandRepository = BrandRepositoryImpl(dao)


    @Provides
    fun provideClothesRepository(
        dao: ClothesDao,
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): ClothesRepository = ClothesRepositoryImpl(firestore, storage, dao)

    @Provides
    fun provideBlockedUserRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): BlockedUserRepository = BlockedUserRepositoryImpl(firestore, auth)

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        dao: UserDao,
        appDatabase: MySizeDatabase,
        @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(auth, firestore, dao, appDatabase, context)
    }

    @Provides
    fun provideFollowRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): FollowRepository = FollowRepositoryImpl(firestore, auth)

    @Provides
    fun provideReportRepository(
        firestore: FirebaseFirestore
    ): ReportRepository = ReportRepositoryImpl(firestore)

    @Provides
    fun provideShopRepository(
        firestore: FirebaseFirestore,
        dao: ShopDao,
        settingsDataStore: SettingsDataStore,
    ): ShopRepository = ShopRepositoryImpl(firestore, dao, settingsDataStore)
}
