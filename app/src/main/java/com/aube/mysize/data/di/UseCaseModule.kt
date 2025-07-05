package com.aube.mysize.data.di

import com.aube.mysize.domain.repository.BrandRepository
import com.aube.mysize.domain.repository.ClothesRepository
import com.aube.mysize.domain.repository.FollowRepository
import com.aube.mysize.domain.repository.ShopRepository
import com.aube.mysize.domain.repository.SizeRepository
import com.aube.mysize.domain.repository.UserRepository
import com.aube.mysize.domain.usecase.brand.DeleteBrandUseCase
import com.aube.mysize.domain.usecase.brand.GetBrandListByCategoryUseCase
import com.aube.mysize.domain.usecase.brand.InsertBrandUseCase
import com.aube.mysize.domain.usecase.clothes.ClearRecentViewsUseCase
import com.aube.mysize.domain.usecase.clothes.DeleteClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetClothesByIdUseCase
import com.aube.mysize.domain.usecase.clothes.GetFollowingsClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetOthersClothesByTagUseCase
import com.aube.mysize.domain.usecase.clothes.GetOthersClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetRecentViewsUseCase
import com.aube.mysize.domain.usecase.clothes.GetUserAllClothesUseCase
import com.aube.mysize.domain.usecase.clothes.GetUserPublicClothesUseCase
import com.aube.mysize.domain.usecase.clothes.InsertClothesUseCase
import com.aube.mysize.domain.usecase.clothes.SaveRecentViewUseCase
import com.aube.mysize.domain.usecase.follow.FollowUserUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowersCountUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowersUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowingsCountUseCase
import com.aube.mysize.domain.usecase.follow.GetFollowingsUseCase
import com.aube.mysize.domain.usecase.follow.UnfollowUserUseCase
import com.aube.mysize.domain.usecase.recommend.RecommendShopsUseCase
import com.aube.mysize.domain.usecase.size.DeleteSizeUseCase
import com.aube.mysize.domain.usecase.size.GetAllSizesUseCase
import com.aube.mysize.domain.usecase.size.GetSizeByIdUseCase
import com.aube.mysize.domain.usecase.size.InsertSizeUseCase
import com.aube.mysize.domain.usecase.user.CacheUserUseCase
import com.aube.mysize.domain.usecase.user.ChangePasswordUseCase
import com.aube.mysize.domain.usecase.user.CheckEmailVerifiedUseCase
import com.aube.mysize.domain.usecase.user.DeleteProfileImageUseCase
import com.aube.mysize.domain.usecase.user.DeleteUserUseCase
import com.aube.mysize.domain.usecase.user.FetchUserFromFirestoreUseCase
import com.aube.mysize.domain.usecase.user.GetUserFromCacheUseCase
import com.aube.mysize.domain.usecase.user.GetUserNicknameUseCase
import com.aube.mysize.domain.usecase.user.PersistUserToFirestoreUseCase
import com.aube.mysize.domain.usecase.user.SearchUsersUseCase
import com.aube.mysize.domain.usecase.user.SendEmailVerificationUseCase
import com.aube.mysize.domain.usecase.user.SignInUseCase
import com.aube.mysize.domain.usecase.user.SignOutUseCase
import com.aube.mysize.domain.usecase.user.SignUpUseCase
import com.aube.mysize.domain.usecase.user.UpdateNicknameUseCase
import com.aube.mysize.domain.usecase.user.UploadProfileImageUseCase
import com.aube.mysize.domain.usecases.BrandUseCases
import com.aube.mysize.domain.usecases.ClothesUseCases
import com.aube.mysize.domain.usecases.FollowUseCases
import com.aube.mysize.domain.usecases.SizeUseCases
import com.aube.mysize.domain.usecases.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    // ───── 사이즈 ─────
    @Provides
    @ViewModelScoped
    fun provideInsertSizeUseCase(
        repository: SizeRepository
    ): InsertSizeUseCase = InsertSizeUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideDeleteSizeUseCase(
        repository: SizeRepository
    ): DeleteSizeUseCase = DeleteSizeUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetAllSizesUseCase(
        repository: SizeRepository
    ): GetAllSizesUseCase = GetAllSizesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetSizeByIdUseCase(
        repository: SizeRepository
    ): GetSizeByIdUseCase = GetSizeByIdUseCase(repository)

    // ───── 브랜드 ─────
    @Provides
    @ViewModelScoped
    fun provideInsertBrandUseCase(
        repository: BrandRepository
    ): InsertBrandUseCase = InsertBrandUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetBrandByCategoryUseCase(
        repository: BrandRepository
    ): GetBrandListByCategoryUseCase = GetBrandListByCategoryUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideDeleteBrandUseCase(
        repository: BrandRepository
    ): DeleteBrandUseCase = DeleteBrandUseCase(repository)

    // ───── 옷 ─────
    @Provides
    @ViewModelScoped
    fun provideInsertClothesUseCase(
        repository: ClothesRepository
    ): InsertClothesUseCase = InsertClothesUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetUserAllClothesUseCase(
        repository: ClothesRepository
    ): GetUserAllClothesUseCase = GetUserAllClothesUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetUserPublicClothesUseCase(
        repository: ClothesRepository
    ): GetUserPublicClothesUseCase = GetUserPublicClothesUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetOthersClothesUseCase(
        repository: ClothesRepository
    ): GetOthersClothesUseCase = GetOthersClothesUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetFollowingsClothesUseCase(
        repository: ClothesRepository
    ): GetFollowingsClothesUseCase = GetFollowingsClothesUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetClothesByIdUseCase(
        repository: ClothesRepository
    ): GetClothesByIdUseCase = GetClothesByIdUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideDeleteClothesUseCase(
        repository: ClothesRepository
    ): DeleteClothesUseCase = DeleteClothesUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetOthersClothesByTagUseCase(
        repository: ClothesRepository
    ): GetOthersClothesByTagUseCase = GetOthersClothesByTagUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideSaveRecentViewUseCase(
        repository: ClothesRepository
    ): SaveRecentViewUseCase = SaveRecentViewUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetRecentViewsUseCase(
        repository: ClothesRepository
    ): GetRecentViewsUseCase = GetRecentViewsUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideClearRecentViewsUseCase(
        repository: ClothesRepository
    ): ClearRecentViewsUseCase = ClearRecentViewsUseCase(repository)

    // ───── 유저 ─────
    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(
        repository: UserRepository
    ): SignUpUseCase = SignUpUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        repository: UserRepository
    ): SignInUseCase = SignInUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideSignOutUseCase(
        repository: UserRepository
    ): SignOutUseCase = SignOutUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideChangePasswordUseCase(
        userRepository: UserRepository
    ): ChangePasswordUseCase = ChangePasswordUseCase(userRepository)
    @Provides
    @ViewModelScoped
    fun provideSendEmailVerificationUseCase(
        userRepository: UserRepository
    ): SendEmailVerificationUseCase = SendEmailVerificationUseCase(userRepository)
    @Provides
    @ViewModelScoped
    fun provideCheckEmailVerifiedUseCase(
        userRepository: UserRepository
    ): CheckEmailVerifiedUseCase = CheckEmailVerifiedUseCase(userRepository)
    @Provides
    @ViewModelScoped
    fun provideDeleteUserUseCase(
        repository: UserRepository
    ): DeleteUserUseCase = DeleteUserUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetUserFromCacheUseCase(
        repository: UserRepository
    ): GetUserFromCacheUseCase = GetUserFromCacheUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetUserNicknameUseCase(
        repository: UserRepository
    ): GetUserNicknameUseCase = GetUserNicknameUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideUpdateNicknameUseCase(
        repository: UserRepository
    ): UpdateNicknameUseCase = UpdateNicknameUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideFetchUserFromFirestoreUseCase(
        repository: UserRepository
    ): FetchUserFromFirestoreUseCase = FetchUserFromFirestoreUseCase(repository)
    @Provides
    @ViewModelScoped
    fun providePersistUserToFirestoreUseCase(
        userRepository: UserRepository
    ): PersistUserToFirestoreUseCase =  PersistUserToFirestoreUseCase(userRepository)
    @Provides
    @ViewModelScoped
    fun provideUploadProfileImageUseCase(
        repository: UserRepository
    ): UploadProfileImageUseCase = UploadProfileImageUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideDeleteProfileImageUseCase(
        repository: UserRepository
    ): DeleteProfileImageUseCase = DeleteProfileImageUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideCacheUserUseCase(
        repository: UserRepository
    ): CacheUserUseCase = CacheUserUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideSearchUsersUseCase(
        repository: UserRepository
    ): SearchUsersUseCase = SearchUsersUseCase(repository)

    // ───── 팔로우 ─────
    @Provides
    @ViewModelScoped
    fun provideFollowUserUseCase(
        repository: FollowRepository
    ): FollowUserUseCase = FollowUserUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideUnfollowUserUseCase(
        repository: FollowRepository
    ): UnfollowUserUseCase = UnfollowUserUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetFollowingCountUseCase(
        repository: FollowRepository
    ): GetFollowingsCountUseCase = GetFollowingsCountUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetFollowerCountUseCase(
        repository: FollowRepository
    ): GetFollowersCountUseCase = GetFollowersCountUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetFollowersUseCase(
        repository: FollowRepository
    ): GetFollowersUseCase = GetFollowersUseCase(repository)
    @Provides
    @ViewModelScoped
    fun provideGetFollowingsUseCase(
        repository: FollowRepository
    ): GetFollowingsUseCase = GetFollowingsUseCase(repository)

    // ───── 쇼핑몰 ─────
    @Provides
    @ViewModelScoped
    fun provideRecommendShopsUseCase(
        repository: ShopRepository
    ): RecommendShopsUseCase = RecommendShopsUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideBrandUseCases(
        insert: InsertBrandUseCase,
        delete: DeleteBrandUseCase,
        getByCategory: GetBrandListByCategoryUseCase
    ) = BrandUseCases(insert, delete, getByCategory)

    @Provides
    @ViewModelScoped
    fun provideClothesUseCases(
        insert: InsertClothesUseCase,
        delete: DeleteClothesUseCase,
        getById: GetClothesByIdUseCase,
        getAll: GetUserAllClothesUseCase,
        getPublic: GetUserPublicClothesUseCase,
        getFollowings: GetFollowingsClothesUseCase,
        getOthersByTag: GetOthersClothesByTagUseCase,
        getOthers: GetOthersClothesUseCase,
        getRecent: GetRecentViewsUseCase,
        saveRecent: SaveRecentViewUseCase,
        clearRecent: ClearRecentViewsUseCase
    ) = ClothesUseCases(
        insert, delete, getById, getAll, getPublic,
        getFollowings, getOthersByTag, getOthers,
        getRecent, saveRecent, clearRecent
    )

    @Provides
    @ViewModelScoped
    fun provideFollowUseCases(
        follow: FollowUserUseCase,
        unfollow: UnfollowUserUseCase,
        getFollowers: GetFollowersUseCase,
        getFollowersCount: GetFollowersCountUseCase,
        getFollowings: GetFollowingsUseCase,
        getFollowingsCount: GetFollowingsCountUseCase,
    ) = FollowUseCases(
        follow, unfollow, getFollowers, getFollowersCount,
        getFollowings, getFollowingsCount
    )

    @Provides
    @ViewModelScoped
    fun provideSizeUseCases(
        insert: InsertSizeUseCase,
        delete: DeleteSizeUseCase,
        getById: GetSizeByIdUseCase,
        getAll: GetAllSizesUseCase
    ) = SizeUseCases(insert, delete, getById, getAll)

    @Provides
    @ViewModelScoped
    fun provideUserUseCases(
        signUp: SignUpUseCase,
        signIn: SignInUseCase,
        signOut: SignOutUseCase,
        changePassword: ChangePasswordUseCase,
        sendVerification: SendEmailVerificationUseCase,
        checkVerified: CheckEmailVerifiedUseCase,
        deleteUser: DeleteUserUseCase,
        getCache: GetUserFromCacheUseCase,
        getNickname: GetUserNicknameUseCase,
        updateNickname: UpdateNicknameUseCase,
        fetchFirestore: FetchUserFromFirestoreUseCase,
        persistFirestore: PersistUserToFirestoreUseCase,
        uploadProfileImage: UploadProfileImageUseCase,
        deleteProfileImage: DeleteProfileImageUseCase,
        cacheUser: CacheUserUseCase,
        search: SearchUsersUseCase
    ) = UserUseCases(
        signUp, signIn, signOut, changePassword,
        sendVerification, checkVerified, deleteUser,
        getCache, getNickname, updateNickname, fetchFirestore, persistFirestore,
        uploadProfileImage, deleteProfileImage, cacheUser, search
    )
}
