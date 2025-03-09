package android.saswat.winmyargument.ui

sealed class Screens(val route:String) {
    data object How : Screens("how")
    data object Why: Screens("why")
    data object About : Screens("about")
    data object SignIn: Screens("signIn")
    data object SignUp: Screens("signUp")
    data object MainScreen: Screens("mainScreen")

}