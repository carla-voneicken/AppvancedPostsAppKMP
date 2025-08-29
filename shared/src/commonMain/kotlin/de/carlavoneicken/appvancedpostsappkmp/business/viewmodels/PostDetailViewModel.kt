package de.carlavoneicken.appvancedpostsappkmp.business.viewmodels

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.CreatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.GetPostByIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.UpdatePostUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.mapNetworkErrorToMessage
import de.carlavoneicken.appvancedpostsappkmp.data.Post
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostDetailViewModel(
    private val postId: Int?, private val userId: Int
): ViewModel(), KoinComponent {
    private val getPostByIdUsecase: GetPostByIdUsecase by inject()
    private val createPostUsecase: CreatePostUsecase by inject()
    private val updatePostUsecase: UpdatePostUsecase by inject()

    data class UiState(
        val postId: Int? = null,
        val userId: Int,
        val post: Post? = null,
        val title: String = "",
        val body: String = "",
        val errorMessage: String? = null,
        val showError: Boolean = false,
        val isSaved: Boolean = false
    )

    // Initiate the UiState
    private val _uiState = MutableStateFlow(viewModelScope, UiState(userId = userId))
    @NativeCoroutinesState
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        postId?.let { id ->
            viewModelScope.launch {
                when (val result = getPostByIdUsecase(id)) {
                    is NetworkResult.Success ->  {
                        val post = result.data
                        _uiState.value = UiState(
                            post = post,
                            userId = userId,
                            title = post.title,
                            body = post.body
                        )
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

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateBody(newBody: String) {
        _uiState.value = _uiState.value.copy(body = newBody)
    }

    suspend fun savePost() {
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
                updatePostUsecase(newPost)
            } else {
                createPostUsecase(newPost)
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

    fun dismissError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            showError = false
        )
    }
}