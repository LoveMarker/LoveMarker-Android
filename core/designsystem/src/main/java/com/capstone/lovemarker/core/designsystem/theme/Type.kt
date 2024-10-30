package com.capstone.lovemarker.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.capstone.lovemarker.core.designsystem.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))
val PretendardLight = FontFamily(Font(R.font.pretendard_light, FontWeight.Light))

@Stable
class LoveMarkerTypography internal constructor(
    headline28B: TextStyle,
    headline24B: TextStyle,
    headline20B: TextStyle,
    headline18B: TextStyle,
    headline18M: TextStyle,
    body16B: TextStyle,
    body16M: TextStyle,
    body15B: TextStyle,
    body15M: TextStyle,
    body14B: TextStyle,
    body14M: TextStyle,
    label13B: TextStyle,
    label13M: TextStyle,
    label12B: TextStyle,
    label12M: TextStyle,
    label11B: TextStyle,
    label11M: TextStyle,
) {
    var headline28B by mutableStateOf(headline28B)
        private set
    var headline24B by mutableStateOf(headline24B)
        private set
    var headline20B by mutableStateOf(headline20B)
        private set
    var headline18B by mutableStateOf(headline18B)
        private set
    var headline18M by mutableStateOf(headline18M)
        private set

    var body16B by mutableStateOf(body16B)
        private set
    var body16M by mutableStateOf(body16M)
        private set
    var body15B by mutableStateOf(body15B)
        private set
    var body15M by mutableStateOf(body15M)
        private set
    var body14B by mutableStateOf(body14B)
        private set
    var body14M by mutableStateOf(body14M)
        private set

    var label13B by mutableStateOf(label13B)
        private set
    var label13M by mutableStateOf(label13M)
        private set
    var label12B by mutableStateOf(label12B)
        private set
    var label12M by mutableStateOf(label12M)
        private set
    var label11B by mutableStateOf(label11B)
        private set
    var label11M by mutableStateOf(label11M)
        private set

    fun copy(
        headline28B: TextStyle = this.headline28B,
        headline24B: TextStyle = this.headline24B,
        headline20B: TextStyle = this.headline20B,
        headline18B: TextStyle = this.headline18B,
        headline18M: TextStyle = this.headline18M,
        body16B: TextStyle = this.body16B,
        body16M: TextStyle = this.body16M,
        body15B: TextStyle = this.body15B,
        body15M: TextStyle = this.body15M,
        body14B: TextStyle = this.body14B,
        body14M: TextStyle = this.body14M,
        label13B: TextStyle = this.label13B,
        label13M: TextStyle = this.label13M,
        label12B: TextStyle = this.label12B,
        label12M: TextStyle = this.label12M,
        label11B: TextStyle = this.label11B,
        label11M: TextStyle = this.label11M,
    ) = LoveMarkerTypography(
        headline28B = headline28B,
        headline24B = headline24B,
        headline20B = headline20B,
        headline18B = headline18B,
        headline18M = headline18M,
        body16B = body16B,
        body16M = body16M,
        body15B = body15B,
        body15M = body15M,
        body14B = body14B,
        body14M = body14M,
        label13B = label13B,
        label13M = label13M,
        label12B = label12B,
        label12M = label12M,
        label11B = label11B,
        label11M = label11M,
    )

    fun update(other: LoveMarkerTypography) {
        headline28B = other.headline28B
        headline24B = other.headline24B
        headline20B = other.headline20B
        headline18B = other.headline18B
        headline18M = other.headline18M

        body16B = other.body16B
        body16M = other.body16M
        body15B = other.body15B
        body15M = other.body15M
        body14B = other.body14B
        body14M = other.body14M

        label13B = other.label13B
        label13M = other.label13M
        label12B = other.label12B
        label12M = other.label12M
        label11B = other.label11B
        label11M = other.label11M
    }
}

@Composable
fun LoveMarkerTypography() = LoveMarkerTypography(
    headline28B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 30.sp
    ),
    headline24B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    headline20B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    headline18B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    headline18M = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    body16B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    body16M = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    body15B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    body15M = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    body14B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    body14M = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    label13B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 18.sp
    ),
    label13M = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp
    ),
    label12B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 17.sp
    ),
    label12M = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 17.sp
    ),
    label11B = TextStyle(
        fontFamily = PretendardBold,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 15.sp
    ),
    label11M = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 15.sp
    ),
)