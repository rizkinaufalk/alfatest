package com.rizki.alfatest.domain.usecase.favourite

import javax.inject.Inject

data class FavouriteUseCase @Inject constructor(
    val addFavourite: AddFavouriteUseCase,
    val deleteFavourite: DeleteFavUseCase,
    val getFavouritebyId: GetFavByIdUseCase,
    val getFavourite: GetFavouriteUseCase
)