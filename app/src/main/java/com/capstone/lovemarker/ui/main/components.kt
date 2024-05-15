package com.capstone.lovemarker.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.domain.model.Todo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodoItem(
    todo: Todo,
    onClick: (todo: Todo) -> Unit = {},
    onDeleteClick: (todo: Todo) -> Unit,
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Row(

    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .clickable { onDeleteClick(todo) },
            imageVector = Icons.Outlined.Delete,
            contentDescription = "delete button",
            tint = Color.Gray
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = dateFormat.format(Date(todo.date)),
                color = if (todo.isDone) Color.Gray else Color.Black,
                style = TextStyle(textDecoration =
                    if (todo.isDone) TextDecoration.LineThrough
                    else TextDecoration.None
                )
            )

            Text(
                text = todo.title,
                color = if (todo.isDone) Color.Gray else Color.Black,
                style = TextStyle(textDecoration =
                if (todo.isDone) TextDecoration.LineThrough
                else TextDecoration.None
                )
            )

            if (todo.isDone) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "done icon",
                    tint = Color.Cyan
                )
            }
        }
    }
}