package br.com.digitalhouse.desafiofirebase.services

import br.com.digitalhouse.desafiofirebase.model.Game
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*


interface Repository {
    suspend fun connectDB()

    suspend fun getGames(): Task<DataSnapshot>

    suspend fun createGame(game: Game, index: String)

    suspend fun updateGame(game: Game, index: String)
}

class RepositoryImplementation : Repository {
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    override suspend fun connectDB() {
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("games")
    }

    override suspend fun getGames(): Task<DataSnapshot> {
        return myRef.get()
    }

    override suspend fun createGame(game: Game, index: String) {
        myRef.child(index).setValue(game)
    }

    override suspend fun updateGame(game: Game, index: String) {
        myRef.child(index).setValue(game)
    }
}