package de.carlavoneicken.appvancedpostsappkmp.business.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.carlavoneicken.appvancedpostsappkmp.business.usecases.GetUserUsecase
import de.carlavoneicken.appvancedpostsappkmp.business.utils.NetworkResult
import de.carlavoneicken.appvancedpostsappkmp.business.utils.mapNetworkErrorToMessage
import de.carlavoneicken.appvancedpostsappkmp.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UsersViewModel(): ViewModel(), KoinComponent {

    private val getUserUsecase: GetUserUsecase by inject()

    data class UiState(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    private fun updateState(update: UiState.() -> UiState) {
        _uiState.value = _uiState.value.update()
    }

    fun loadUsers() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

            when (val result = getUserUsecase()) {
                is NetworkResult.Success -> updateState {
                    copy(users = result.data, isLoading = false)
                }
                is NetworkResult.Failure -> updateState {
                    copy(errorMessage = mapNetworkErrorToMessage(result.error), isLoading = false)
                }
            }
        }
    }
}