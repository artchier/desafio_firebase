package br.com.digitalhouse.desafiofirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.desafiofirebase.model.Game
import br.com.digitalhouse.desafiofirebase.services.RepositoryImplementation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class MyViewModel() : ViewModel() {
    private val repositoryImplementation = RepositoryImplementation()

    private var _allGames = MutableLiveData<ArrayList<Game>>()
    val allGames: LiveData<ArrayList<Game>>
        get() = _allGames

    var _scrollCoordinates = MutableLiveData<IntArray>()
    private val scrollCoordinates: MutableLiveData<IntArray>
        get() = _scrollCoordinates

    fun updateScrollCoordinates(coordinates: IntArray) {
        scrollCoordinates.value = coordinates
    }

    fun connectDBTask() {
        viewModelScope.launch {
            repositoryImplementation.connectDB()
        }
    }

    fun getGamesTask() {
        val values = arrayListOf<Game>()
        viewModelScope.launch {
            repositoryImplementation.getGames()
            repositoryImplementation.myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    dataSnapshot.children.forEach {
                        val game = it.value as HashMap<*, *>
                        values.add(
                            Game(
                                game["cover"].toString(),
                                game["title"].toString(),
                                game["year"].toString(),
                                game["description"].toString()
                            )
                        )
                    }
                    _allGames.value = values
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        }
    }

    fun createGameTask(game: Game, index: String) {
        viewModelScope.launch {
            repositoryImplementation.createGame(game, index)
        }
    }

    fun updateGameTask(game: Game, index: String) {
        viewModelScope.launch {
            repositoryImplementation.myRef.child(index).setValue(game)
        }
    }
}