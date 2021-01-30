package br.com.digitalhouse.desafiofirebase.services

import br.com.digitalhouse.desafiofirebase.model.Game
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


interface Repository {
    suspend fun connectDB()

    suspend fun getStorageReference()

    suspend fun getGames(): Task<DataSnapshot>

    suspend fun createGame(game: Game, index: String)

    suspend fun updateGame(game: Game, index: String)

    suspend fun removeGame(index: String)
}

class RepositoryImplementation : Repository {
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var storageReference: StorageReference

    override suspend fun connectDB() {
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("games")
    }

    override suspend fun getStorageReference() {
        storageReference = FirebaseStorage.getInstance().reference
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

    override suspend fun removeGame(index: String) {
        myRef.child(index).removeValue()
    }
}