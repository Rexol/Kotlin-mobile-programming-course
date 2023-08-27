package com.example.loginform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.window.Dialog
import com.example.loginform.ui.theme.LoginFormTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginFormTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginForm()
                }
            }
        }
    }
}

@Composable
fun LoginForm() {
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var pswdHidden: Boolean by remember { mutableStateOf(true) }
    var dialogOpen: Boolean by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.padding(8.dp)
    ){
        Text(
            text = stringResource(R.string.login_header),
            color = MaterialTheme.colors.primary,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = stringResource(R.string.email_label))},
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            trailingIcon = {Icons.Filled.Email},
        )
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = stringResource(R.string.password_label))},
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (pswdHidden) PasswordVisualTransformation() else VisualTransformation.None ,
            trailingIcon = {
                val icon = if (pswdHidden) Icons.Outlined.Star else Icons.Outlined.Lock

                val description = if (pswdHidden) stringResource(R.string.password_desc_show) else stringResource(
                                    R.string.password_desc_hide)
                IconButton(onClick = { pswdHidden = !pswdHidden}) {
                    Icon(imageVector = icon, description)
                }
            }
        )
        OutlinedButton(onClick = { dialogOpen = true }) {
            Text( stringResource(R.string.submit_btn) )
        }
        if (dialogOpen) {
            AlertDialog(
                onDismissRequest = { dialogOpen = false },
                title = { Text(text = stringResource(R.string.login_alert_title)) },
                text = { Text(text = stringResource(R.string.login_alert_success)) },
                confirmButton = {
                    Button(onClick = {
                        dialogOpen = false
                        email = ""
                        password = ""
                    }) {
                        Text(text = stringResource(R.string.login_alert_confirm_btn))

                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginFormTheme {
        LoginForm()
    }
}