package com.example.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlinflow.ui.theme.KotlinFlowTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinFlowTheme {
                val viewModel by viewModels<CombineViewModel>()
                val zipViewModel = ZipViewModel()
                val profileState by viewModel.profileFlow.collectAsState()
                val scope= rememberCoroutineScope()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        profileState?.let {
                            Text(text = "$profileState")
                        }

                        Button(onClick = {
                            scope.launch {
                                viewModel.updateUser(
                                    users[Random.nextInt(0, 4)]
                                )
                            }
                        }) {
                            Text(text = "Add User")
                        }

                        Button(onClick = {
                           scope.launch {
                               viewModel.updatePost(posts[Random.nextInt(0,4)])
                           }
                        }) {
                            Text(text = "Add Post")
                        }

                        Button(onClick = {
                            scope.launch {
                                viewModel.updatePost(
                                    posts[Random.nextInt(0, 4)]
                                )

                                viewModel.updatePost(
                                    posts[Random.nextInt(0, 4)]
                                )
                            }
                        }) {
                            Text(text = "Add Both")
                        }
                    }
                }
            }
        }
    }
}

val users = listOf(
    User("Bahman", "google.com/Bahman.jpg", "User_Bahman"),
    User("Ali", "google.com/Ali.jpg", "User_Ali"),
    User("Hasan", "google.com/Hasan.jpg", "User_Hasan"),
    User("Kamran", "google.com/Kamran.jpg", "User_Kamran"),
    User("Sadegh", "google.com/Sadegh.jpg", "User_Sadegh"),
)

val posts = listOf(
    Post("post ${Random.nextInt()}", "instagram.post/${Random.nextInt()}", "User_Bahman"),
    Post("post ${Random.nextInt()}", "instagram.post/${Random.nextInt()}", "User_Ali"),
    Post("post ${Random.nextInt()}", "instagram.post/${Random.nextInt()}", "User_Hasan"),
    Post("post ${Random.nextInt()}", "instagram.post/${Random.nextInt()}", "User_Kamran"),
    Post("post ${Random.nextInt()}", "instagram.post/${Random.nextInt()}", "User_Sadegh"),
)