package com.capstone.lovemarker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    // state 변할 때마다 recomposition 발생
    val text1: MutableState<String> = remember {
        mutableStateOf("Hello")
    }

    // by -> delegate pattern (getter, setter 재정의)
    var text2: String by remember {
        mutableStateOf("World")
    }

    // Destructuring -> TextField
    val (text: String, setText: (String) -> Unit) = remember {
        mutableStateOf("Hello World")
    }

    Column {
        Text("Hello World")
        Button(onClick = {
            text1.value = "변경 1"
            print(text1.value)

            text2 = "변경 2"
            print(text2)

            setText("변경 3")

            viewModel.updateValue("변경 4")
        }) {
            Text("클릭")
        }
        TextField(value = text, onValueChange = setText)
    }
}

class MainViewModel : ViewModel() {
    private val _state = mutableStateOf("Hello")
    val state: State<String> = _state

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    private val _flow = MutableStateFlow("Hello")
    val flow: StateFlow<String> = _flow.asStateFlow()

    fun updateValue(value: String) {
        _state.value = value
    }
}