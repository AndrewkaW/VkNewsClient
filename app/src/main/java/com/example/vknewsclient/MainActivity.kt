@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkNewsClientTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    //     PreviewVkPostLite()
                    Test4()
                }
            }
        }
    }
}

@Composable
private fun Test() {
    OutlinedButton(
        onClick = {},
    ) {
        Text(
            text = "Hello World"
        )
    }
}

@Composable
private fun Test2() {
    TextField(value = "Value", onValueChange = {}, label = { Text(text = "Label") }
    )
}

@Composable
private fun Test3() {
    BasicAlertDialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Are you sure?",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                )

                Text(
                    text =
                        "Do you want delete this file?."
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = { },
                    ) {
                        Text(text = "No", color = MaterialTheme.colorScheme.onPrimary)
                    }
                    TextButton(
                        onClick = { },
                    ) {

                        Text(text = "Yes", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }


            }
        }

    }

}

@Preview
@Composable
private fun Test4() {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                val itemList = listOf(
                    Icons.Filled.Add,
                    Icons.Filled.Close,
                    Icons.Filled.AddCircle,
                    Icons.Filled.Check
                )
                itemList.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.name.substringAfterLast(".")) },
                        icon = {
                            Icon(
                                imageVector = item,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )
                        },
                        selected = false,
                        onClick = {}
                    )
                }
            }
        }
    ) { }
    Scaffold(
        content = {
            Text(modifier = Modifier.padding(it), text = "This is the scaffolds content")
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Title TopBar")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
//            BottomAppBar {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = {}) {
//                        Icon(
//                            imageVector = Icons.Filled.Add,
//                            tint = MaterialTheme.colorScheme.primary,
//                            contentDescription = null
//                        )
//                    }
//
//                    IconButton(onClick = {}) {
//                        Icon(
//
//                            imageVector = Icons.Filled.Close,
//                            tint = MaterialTheme.colorScheme.primary,
//                            contentDescription = null
//                        )
//                    }
//
//                }
//            }
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Date")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Account")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Settings")
                    }
                )
            }
        },
    )
}