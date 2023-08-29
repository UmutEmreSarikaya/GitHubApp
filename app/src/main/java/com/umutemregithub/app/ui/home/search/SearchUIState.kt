package com.umutemregithub.app.ui.home.search

sealed class SearchUIState{
    object Loading: SearchUIState()
    object Loaded: SearchUIState()

}
