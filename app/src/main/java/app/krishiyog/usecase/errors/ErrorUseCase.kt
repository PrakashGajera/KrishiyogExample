package app.krishiyog.usecase.errors

import app.krishiyog.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
