package de.carlavoneicken.appvancedpostsappkmp.business.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.GetPostByUserIdUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.mapNetworkErrorToMessage
import de.carlavoneicken.appvancedpostsappkmp.data.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostsViewModel(
    private val userId: Int
): ViewModel(), KoinComponent {
    // inject needed Usecase
    private val getPostsByUserIdUsecase: GetPostByUserIdUsecase by inject()

    // UI State data class
    data class UiState(
        val userId: Int,
        val posts: List<Post> = emptyList(),
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    )

    /*
    private val _uiState = MutableStateFlow(UiState())
    -> creates a MutableStateFlow with an initial value of UiState() (empty state)
    MutableStateFlow: like a container that holds a value and can notify observers when the value changes
    private: only the ViewModel can modify it
    _: naming convention for private backing properties

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    -> creates a public, READ-ONLY view of the private _uiState
    StateFlow: can only be observed (not mutable)
    asStateFlow(): converts the mutable version to a read-only version
    public: UI components can observe it
     */
    private val _uiState = MutableStateFlow(UiState(userId = userId))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    private fun updateState(update: UiState.() -> UiState) {
        _uiState.value = _uiState.value.update()
    }

    fun loadPosts() {
        viewModelScope.launch {
            // copy() creates a new instance of the data class with some properties changed
            // copy(isLoading = true) creates a new instance where the isLoading property is changed, but not the others
            updateState { copy(isLoading = true, errorMessage = null) }

            when (val result = getPostsByUserIdUsecase(userId)) {
                // when httpRequest was successful -> save the data in the uiState
                is NetworkResult.Success -> updateState {
                    copy(posts = result.data, isLoading = false)
                }
                // when httpRequest was not successful -> display errorMessage
                is NetworkResult.Failure -> updateState {
                    copy(errorMessage = mapNetworkErrorToMessage(result.error), isLoading = false)
                }
            }
        }
    }


}