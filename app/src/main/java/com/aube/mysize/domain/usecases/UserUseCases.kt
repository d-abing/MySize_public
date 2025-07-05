package com.aube.mysize.domain.usecases

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

data class UserUseCases(
    val signUp: SignUpUseCase,
    val signIn: SignInUseCase,
    val signOut: SignOutUseCase,
    val changePassword: ChangePasswordUseCase,
    val sendEmailVerification: SendEmailVerificationUseCase,
    val checkEmailVerified: CheckEmailVerifiedUseCase,
    val deleteUser: DeleteUserUseCase,
    val getUserFromCache: GetUserFromCacheUseCase,
    val getUserNickName: GetUserNicknameUseCase,
    val updateNickname: UpdateNicknameUseCase,
    val fetchUserFromFirestore: FetchUserFromFirestoreUseCase,
    val persistUserToFirestore: PersistUserToFirestoreUseCase,
    val uploadProfileImage: UploadProfileImageUseCase,
    val deleteProfileImage: DeleteProfileImageUseCase,
    val cacheUser: CacheUserUseCase,
    val searchUsers: SearchUsersUseCase
)
