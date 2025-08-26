package de.carlavoneicken.appvancedpostsappkmp.presentation

import android.R.attr.onClick
import android.graphics.fonts.Font
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.carlavoneicken.appvancedpostsappkmp.data.Post

@Composable
fun PostItem(
    post: Post,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("postDetail/${post.id}") }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = post.title,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = post.body,
            fontSize = 14.sp
        )
    }
}
