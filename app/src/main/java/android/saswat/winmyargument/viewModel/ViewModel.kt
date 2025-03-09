package android.saswat.winmyargument.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel: ViewModel() {
    /**
     * private val auth=FirebaseAuth.getInstance()
     * private val db = FirebaseFirestore.getInstance()
     *
     * private val _authState=MutableStateFlow<AuthState>(AuthState.Initial)
     * val authstate=_authState.asStateFlow()
     *
     * private val _currentUser = MutableStateFlow<User?>(null)
     * val currentUser=_currentUser.asStateFlow()
     *
     * init{
     *  _authState.value=AuthState.Initial
     *
     *  val currentUser=auth.currentUser
     *      if(currentUser != null) {
     *          _currentUser.value = User(id = currentUser.uid, name = currentUser.displayName ?: "", email = currentUser.email ?: "")
     *      }
     * }
     *
     *
     * */




    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()
    
    init {
        // We'll set the initial state but not attempt auto sign-in
        _authState.value = AuthState.Initial
        
        // Store the current user if available, but don't change auth state
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _currentUser.value = User(id = currentUser.uid, name = currentUser.displayName ?: "", email = currentUser.email ?: "")
        }
    }
    
    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }
        
        _authState.value = AuthState.Loading
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let { user ->
                    fetchUserData(user.uid)
                }
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Sign in failed")
            }
    }
    
    fun signUp(email: String, password: String, name: String) {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            _authState.value = AuthState.Error("All fields must be filled")
            return
        }
        
        _authState.value = AuthState.Loading
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let { user ->
                    val newUser = User(
                        id = user.uid,
                        name = name,
                        email = email
                    )
                    saveUserToFirestore(newUser)
                }
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Sign up failed")
            }
    }
    
    fun signOut() {
        auth.signOut()
        _currentUser.value = null
        _authState.value = AuthState.SignedOut
    }
    
    
    /**
    * Checks if the current user exists in Firestore
    * @param callback A function that will be called with a boolean indicating if the user exists
    */
    fun checkUserExists(callback: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            callback(false)
            return
        }
        
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val exists = document != null && document.exists() && !document.data.isNullOrEmpty()
                callback(exists)
            }
            .addOnFailureListener {
                // In case of failure, assume user doesn't exist
                callback(false)
            }
    }
    
    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _authState.value = AuthState.Error("Email cannot be empty")
            return
        }
        
        _authState.value = AuthState.Loading
        
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _authState.value = AuthState.PasswordResetSent
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Failed to send reset email")
            }
    }
    
    fun checkCurrentSession() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fetchUserData(currentUser.uid)
        } else {
            _authState.value = AuthState.Initial
        }
    }
    
    private fun fetchUserData(userId: String) {
        // First set loading state
        _authState.value = AuthState.Loading
        
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists() && !document.data.isNullOrEmpty()) {
                    val user = document.toObject(User::class.java)
                    _currentUser.value = user
                    _authState.value = AuthState.SignedIn
                } else {
                    // User document doesn't exist in Firestore
                    _authState.value = AuthState.UserNotFound
                    // Sign out the user since their data is missing
                    auth.signOut()
                }
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Failed to fetch user data")
            }
    }
    
    private fun saveUserToFirestore(user: User) {
        db.collection("users").document(user.id)
            .set(user)
            .addOnSuccessListener {
                _currentUser.value = user
                _authState.value = AuthState.SignedIn
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Failed to save user data")
            }
    }
    
    sealed class AuthState {
        object Initial : AuthState()
        object Loading : AuthState()
        object SignedIn : AuthState()
        object SignedOut : AuthState()
        object PasswordResetSent : AuthState()
        object UserNotFound : AuthState()
        data class Error(val message: String) : AuthState()
    }
    
    data class User(
        val id: String = "",
        val name: String = "",
        val email: String = ""
    )
}