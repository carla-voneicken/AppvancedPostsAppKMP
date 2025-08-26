package de.carlavoneicken.appvancedpostsappkmp.business.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.CreatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.UpdatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.mapNetworkErrorToMessage
import de.carlavoneicken.appvancedpostsappkmp.data.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostDetailViewModel(
    post: Post?, userId: Int
): ViewModel(), KoinComponent {
    // inject needed Usecases
    private val createPostUseCase: CreatePostUsecase by inject()
    private val updatePostUseCase: UpdatePostUsecase by inject()

    data class UiState(
        val post: Post?,
        val userId: Int,
        val title: String,
        val body: String,
        val errorMessage: String? = null,
        val showError: Boolean = false,
        val isSaved: Boolean = false
    )

    // Initiate the UiState
    private val _uiState = MutableStateFlow(
        if (post != null) {
            // Editing an existing post
            UiState(
                post = post,
                userId = userId,
                title = post.title,
                body = post.body
            )
        } else {
            // Creating a new post
            UiState(
                post = null,
                userId = userId,
                title = "",
                body = ""
            )
        }
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun savePost() {
        viewModelScope.launch {
            val currentState = _uiState.value

            val newPost = Post(
                userId = currentState.userId,
                id = currentState.post?.id ?: 0,
                title = currentState.title,
                body = currentState.body
            )

            // clear any previous errors
            _uiState.value = currentState.copy(
                errorMessage = null,
                showError = false
            )

            // update or create post
            val result = if (currentState.post != null) {
                updatePostUseCase(newPost)
            } else {
                createPostUseCase(newPost)
            }

            // check if the network request was successful, if not display errorMessage
            when (result) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(isSaved = true)
                }
                is NetworkResult.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = mapNetworkErrorToMessage(result.error),
                        showError = true
                    )
                }
            }
        }
    }
}