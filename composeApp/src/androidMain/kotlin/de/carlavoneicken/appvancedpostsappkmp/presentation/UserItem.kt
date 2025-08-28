package de.carlavoneicken.appvancedpostsappkmp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.carlavoneicken.appvancedpostsappkmp.data.User

@Composable
fun UserItem(
    user: User,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Username",
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = user.username
        )
    }
}

@Preview
@Composable
fun UserItemPreview() {
    UserItem(
        user = User(id = 1, username = "Joodie"),
        onClick = {} // do nothing for preview
    )
}
