package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable


fun MyEditText(
    value: String,
    placeholder: String,
    onValueChange: (it: String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    onError: Boolean = false,
) {

    val borderColor = if (onError) Color.Red else Color.LightGray
    val PlaceholderColor = if (onError) Color.Red else Color.DarkGray


    TextField(
        modifier = Modifier.run {
            width(250.dp)
                .height(52.dp)
                .border(
                    0.9.dp,
                    color = borderColor,
                    RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
        },
        value = value,
        isError = onError,
        singleLine = true,

        leadingIcon = {
            Icon(
                Icons.Filled.Phone,
                contentDescription = "phone icon",
                tint = Color.Black
            )
        },
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorTextColor = Color.Red,
            errorIndicatorColor = Color.Red
        ),
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(
                text = placeholder,
                color = PlaceholderColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Right
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Start,
            textDirection = TextDirection.Content
        ),
    )
    if (onError) {
        Text(
            text = "شماره موبایل معتبر وارد کنید", color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)

        )
    }
}
