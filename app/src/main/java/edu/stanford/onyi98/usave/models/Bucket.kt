package edu.stanford.onyi98.usave.models

data class Bucket(
    // To do translation between differently named attributes in Firebase and Android Studio do:
    // @get:PropertyName("FirebaseName") @set:PropertyName ("FirebaseName") var firebase_name: String = ""
    var description: String = "",
    // @get:PropertyName("FirebaseName") @set:PropertyName ("FirebaseName")
    var image_url: String = "",
    // @get:PropertyName("FirebaseName") @set:PropertyName ("FirebaseName")
    var goal_amount: Long = 0,
    var name: String = "",
    var user: User? = null
)