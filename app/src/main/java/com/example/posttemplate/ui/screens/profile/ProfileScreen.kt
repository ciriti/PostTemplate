package com.example.posttemplate.ui.screens.profile

//@Composable
//fun ProfileScreen(
//    state: ProfileState,
//    onBack: () -> Unit
//) {
//    when {
//        state.isLoading -> LoadingIndicator()
//        state.errorMessage != null -> Text("Error: ${state.errorMessage}")
//        else -> Text("Hello, ${state.user?.fullName}")
//    }
//}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posttemplate.domain.models.Address
import com.example.posttemplate.domain.models.Company
import com.example.posttemplate.domain.models.User
import com.example.posttemplate.ui.components.LoadingIndicator

@Composable
fun ProfileScreen(
    state: ProfileState,
    onBack: () -> Unit
) {
    when {
        state.isLoading -> LoadingIndicator()
        state.errorMessage != null -> ErrorMessage(state.errorMessage, onBack)
        else -> {
            state.user?.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header with user name
                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    // Email
                    SelectionContainer {
                        Text(
                            text = "Email: ${user.email}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Address Section
                    Text(
                        text = "Address",
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
                        modifier = Modifier.padding(vertical = 12.dp),
                        textAlign = TextAlign.Center
                    )
                    AddressSection(user)

                    // Phone & Website
                    ContactSection(user)

                    // Company Section
                    CompanySection(user)
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(message: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Go Back")
        }
    }
}

@Composable
fun AddressSection(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Street: ${user.address?.street ?: "N/A"}")
            Text(text = "Suite: ${user.address?.suite ?: "N/A"}")
            Text(text = "City: ${user.address?.city ?: "N/A"}")
            Text(text = "Zipcode: ${user.address?.zipcode ?: "N/A"}")
        }
    }
}

@Composable
fun ContactSection(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Phone: ${user.phone}")
            Text(text = "Website: ${user.website}")
        }
    }
}

@Composable
fun CompanySection(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Company: ${user.company?.name ?: "N/A"}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(text = "CatchPhrase: ${user.company?.catchPhrase ?: "N/A"}")
            Text(text = "BS: ${user.company?.bs ?: "N/A"}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    MaterialTheme {
        ProfileScreen(
            state = ProfileState(
                isLoading = false,
                user = User(
                    id = 1,
                    fullName = "Leanne Graham",
                    email = "Sincere@april.biz",
                    address = Address(
                        street = "Kulas Light",
                        suite = "Apt. 556",
                        city = "Gwenborough",
                        zipcode = "92998-3874"
                    ),
                    phone = "1-770-736-8031 x56442",
                    website = "hildegard.org",
                    company = Company(
                        name = "Romaguera-Crona",
                        catchPhrase = "Multi-layered client-server neural-net",
                        bs = "harness real-time e-markets"
                    )
                )
            ),
            onBack = { /* Handle back */ }
        )
    }
}
