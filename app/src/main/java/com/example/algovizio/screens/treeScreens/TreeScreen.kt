package com.example.algovizio.screens.treeScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.algovizio.model.DrawEdge
import com.example.algovizio.model.DrawNode
import com.example.algovizio.viewmodels.TreeViewModel

@Composable
fun TreeScreen(navController: NavController, viewModel: TreeViewModel = viewModel(), algorithm : String){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Tree Visualizer: $algorithm",
            modifier = Modifier.padding(16.dp)
        )
        val nodes = listOf(
            DrawNode(8, 500f, 100f),
            DrawNode(4, 300f, 250f),
            DrawNode(12, 700f, 250f),
            DrawNode(2, 400f, 400f),
            DrawNode(6, 600f, 400f),
            DrawNode(1, 200f, 500f),
            DrawNode(3, 600f, 500f),
            DrawNode(7, 800f, 500f)
        )

        val edges = listOf(
            DrawEdge(500f, 100f, 300f, 250f),
            DrawEdge(500f, 100f, 700f, 250f),
            DrawEdge(300f, 250f, 400f, 400f),
            DrawEdge(700f, 250f, 600f, 400f),
            DrawEdge(200f, 500f, 400f, 400f),
            DrawEdge(600f, 400f, 800f, 500f),
            DrawEdge(200f, 500f, 600f, 400f)


        )

        TreeCanvas(nodes, edges)

    }
}

@Composable
fun TreeCanvas(nodes: List<DrawNode>,
               edges: List<DrawEdge>) {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔹 Draw edges first
        edges.forEach { edge ->
            drawLine(
                color = Color.Gray,
                start = Offset(edge.startX, edge.startY),
                end = Offset(edge.endX, edge.endY),
                strokeWidth = 5f
            )
        }

        // 🔹 Draw nodes
        nodes.forEach { node ->

            // Circle
            drawCircle(
                color = Color.Blue,
                radius = 40f,
                center = Offset(node.x, node.y)
            )

            // Text (value)
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    node.value.toString(),
                    node.x,
                    node.y + 12f,
                    android.graphics.Paint().apply {
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 40f
                        color = android.graphics.Color.WHITE
                    }
                )
            }
        }
    }
}