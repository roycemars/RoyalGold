package com.roycemars.royalgold.feature.expenses.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
//import com.roycemars.royalgold.core.ui.theme.onTertiaryContainerDark
//import com.roycemars.royalgold.core.ui.theme.primaryDark
//import com.roycemars.royalgold.core.ui.theme.primaryLight
//import com.roycemars.royalgold.core.ui.theme.tertiaryContainerDark
import com.roycemars.royalgold.feature.expenses.domain.ExpenseItem

@Composable
fun ExpenseListItem(item: ExpenseItem, @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.Companion) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.Companion
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.Companion.weight(1f)) {
                Text(
                    text = item.name.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
                Text(
                    text = item.amount,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Companion.Bold,
                )
                if (item.goal != null && item.progress != null) {
                    Spacer(modifier = Modifier.Companion.height(8.dp))
                    LinearProgressIndicator(
                        progress = { item.progress },
                        modifier = Modifier.Companion.fillMaxWidth(0.8f),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    )
                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Goal:",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Text(
                            item.goal,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Companion.SemiBold,
                        )
                    }
                }
            }
            Icon(
                imageVector = item.icon,
                contentDescription = item.name,
                modifier = Modifier.Companion.size(36.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}