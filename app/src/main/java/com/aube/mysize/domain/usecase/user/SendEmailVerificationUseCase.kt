package com.aube.mysize.domain.usecase.user

import com.aube.mysize.domain.repository.UserRepository
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repo.sendEmailVerification()
    }
}
