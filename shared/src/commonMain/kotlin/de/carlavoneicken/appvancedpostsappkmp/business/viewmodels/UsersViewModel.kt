package de.carlavoneicken.appvancedpostsappkmp.business.viewmodels

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.ObserveUsersUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.mapNetworkErrorToMessage
import de.carlavoneicken.appvancedpostsappkmp.data.models.User
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.launch
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.RefreshUsersUsecase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UsersViewModel(): ViewModel(), KoinComponent {

    private val observeUsersUsecase: ObserveUsersUsecase by inject()
    private val refreshUsersUsecase: RefreshUsersUsecase by inject()

    data class UiState(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(viewModelScope, UiState())
    @NativeCoroutinesState
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // observeUsersUsecase() returns a Flow<List<User>> -> every time the users table changes, it emits a new list
            // .collect starts listening to that Flow -> each change triggers updateState which replaces the users filed in the UiState
            observeUsersUsecase().collect { users ->
                updateState { copy(users = users)}
            }
        }
        onRefresh()
    }

    private fun updateState(update: UiState.() -> UiState) {
        _uiState.value = _uiState.value.update()
    }

    fun onRefresh() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

            when (val result = refreshUsersUsecase()) {
                is NetworkResult.Success -> updateState {
                    copy(isLoading = false)
                }
                is NetworkResult.Failure -> updateState {
                    copy(errorMessage = mapNetworkErrorToMessage(result.error), isLoading = false)
                }
            }
        }
    }
}