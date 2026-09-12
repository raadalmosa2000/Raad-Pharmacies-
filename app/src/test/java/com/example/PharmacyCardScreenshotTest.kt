package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.ui.screens.PharmacyCard
import com.example.ui.theme.PharmaciesTheme
import com.example.ui.theme.StatusOpen
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class PharmacyCardScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun pharmacyCard_screenshot() {
    composeTestRule.setContent { 
        PharmaciesTheme { 
            PharmacyCard(
                name = "صيدلية الشفاء",
                region = "إدلب - معرة النعمان",
                status = "مفتوحة الآن",
                statusColor = StatusOpen,
                distance = "2.4 كم",
                hours = "8:00 صباحًا - 12:00 منتصف الليل",
                onClick = {}
            )
        } 
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/pharmacy_card.png")
  }
}
