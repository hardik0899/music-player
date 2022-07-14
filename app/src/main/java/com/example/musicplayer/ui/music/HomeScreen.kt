package com.example.musicplayer.ui.music

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.musicplayer.data.model.Music
import com.example.musicplayer.ui.theme.MusicPlayerTheme

private val dummyMusicList = listOf(
    Music(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Hood",
        data = "",
        duration = 12345,
        title = "Android Programming"
    ),
    Music(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Lab",
        data = "",
        duration = 25678,
        title = "Android Programming"
    ),
    Music(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Android Lab",
        data = "",
        duration = 8765454,
        title = "Android Programming"
    ),
    Music(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Kotlin Lab",
        data = "",
        duration = 23456,
        title = "Android Programming"
    ),
    Music(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "KMM Lab",
        data = "",
        duration = 34567,
        title = "Android Programming"
    )
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    isMusicPlaying: Boolean,
    musicList: List<Music>,
    currentlyPlayingMusic: Music?,
    onStart: (Music) -> Unit,
    onItemClick: (Music) -> Unit,
    onNext: () -> Unit

) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    val animatedHeight by animateDpAsState(
        targetValue = if(currentlyPlayingMusic == null) 0.dp
        else BottomSheetScaffoldDefaults.SheetPeekHeight)

    BottomSheetScaffold(
        sheetContent = {
            currentlyPlayingMusic?.let { currentlyPlayingMusic ->
                BottomBarPlayer(
                    progress = progress,
                    onProgressChange = onProgressChange,
                    music = currentlyPlayingMusic,
                    isMusicPlaying = isMusicPlaying,
                    onStart = { onStart.invoke(currentlyPlayingMusic) },
                    onNext = { onNext.invoke() })
            }
        }, scaffoldState = scaffoldState,
           sheetPeekHeight = animatedHeight
    ) {

    }

}

@Composable
fun BottomBarPlayer(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    music: Music,
    isMusicPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArtistInfo(
                music = music,
                modifier = Modifier.weight(1f)
            )

            MediaPlayerController(
                isMusicPlaying = isMusicPlaying,
                onStart = { onStart.invoke() },
                onNext = { onNext.invoke() })
        }

        Slider(
            value = progress,
            onValueChange = { onProgressChange.invoke(it) },
            valueRange = 0f..100f
        )
    }
}

@Composable
fun MediaPlayerController(
    isMusicPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .padding(4.dp)
    ) {
        PlayerIconItem(
            icon = if (isMusicPlaying) Icons.Default.Pause
            else Icons.Default.PlayArrow,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            onStart.invoke()
        }
        Spacer(modifier = Modifier.size(8.dp))
        Icon(imageVector = Icons.Default.SkipNext,
            contentDescription = null,
            modifier = Modifier.clickable {
                onNext.invoke()
            })
    }
}

@Composable
fun ArtistInfo(
    modifier: Modifier = Modifier,
    music: Music
) {
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerIconItem(
            icon = Icons.Default.MusicNote,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface
            )
        ) {}
        Spacer(modifier = Modifier.size(4.dp))

        Column {
            Text(
                text = music.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Clip,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = music.artist,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
        }
    }
}

@Composable
fun PlayerIconItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    border: BorderStroke? = null,
    backgroundColor: Color = MaterialTheme.colors.onSurface,
    color: Color = MaterialTheme.colors.onSurface,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        border = border,
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick.invoke()
            },
        contentColor = color,
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPrev() {
    MusicPlayerTheme {
        BottomBarPlayer(
            progress = 50f,
            onProgressChange = {},
            music = dummyMusicList[0],
            isMusicPlaying = true,
            onStart = { /*TODO*/ }) {
            
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPrev() {
    MusicPlayerTheme {
        HomeScreen(
            progress = 50f,
            onProgressChange = {},
            isMusicPlaying = true,
            musicList = dummyMusicList,
            currentlyPlayingMusic = dummyMusicList[0],
            onStart = {},
            onItemClick = {}
        ) {
            
        }
    }
}