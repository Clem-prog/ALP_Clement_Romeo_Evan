package com.example.alp_clement_romeo_evan.views.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun categoriesButton(
    categoryName: String,
    isSelected: Boolean,
    onCategorySelected: (String) -> Unit
) {
    OutlinedButton(
        onClick = { onCategorySelected(categoryName) },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) Color(0xFFFFD2D9) else Color(0xFFFFFFFF),
            contentColor = if (isSelected) Color.Black else Color(0xFFBDBDBD)
        ),
        border = BorderStroke(1.dp, Color(0xFFBDBDBD)),
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Text(categoryName)
    }
}